package com.risen.base.api.gateway.cache;

import com.google.common.cache.*;
import com.risen.base.api.gateway.config.ApiGatewayServerProperties;
import com.risen.base.api.gateway.mapper.GwAppInfoMapper;
import com.risen.base.api.gateway.mapper.GwAppServerMapper;
import com.risen.base.api.gateway.model.GwAppInfo;
import com.risen.base.api.gateway.model.GwAppServer;
import com.risen.base.api.gateway.util.BeanCopierUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Default impl {@link AppServerCache}
 *
 * @author mengxr
 * @since 1.0
 */
public class DefaultAppServerCache implements AppServerCache {

    private static final Logger log = LoggerFactory.getLogger(DefaultAppServerCache.class);

    private ApiGatewayServerProperties apiGatewayServerProperties;

    private final ConcurrentMap<String, AppServer> readOnlyCacheMap = new ConcurrentHashMap<String, AppServer>();

    private final LoadingCache<String, Value> readWriteCacheMap;

    private final long serverCacheUpdateIntervalMs;

    private final boolean shouldUseReadOnlyServerCache;

    private GwAppInfoMapper gwAppInfoMapper;

    private GwAppServerMapper gwAppServerMapper;

    private ScheduledExecutorService appServerExecutorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("APP-Server-schedule-pool-%d").daemon(true).build());

    public DefaultAppServerCache(ApiGatewayServerProperties apiGatewayServerProperties, GwAppInfoMapper gwAppInfoMapper
            , GwAppServerMapper gwAppServerMapper) {
        this.gwAppInfoMapper = gwAppInfoMapper;
        this.gwAppServerMapper = gwAppServerMapper;
        this.apiGatewayServerProperties = apiGatewayServerProperties;
        this.shouldUseReadOnlyServerCache = apiGatewayServerProperties.getShouldUseReadOnlyServerCache();
        this.serverCacheUpdateIntervalMs = apiGatewayServerProperties.getServerCacheUpdateIntervalMs();

        this.readWriteCacheMap =
                CacheBuilder.newBuilder().initialCapacity(apiGatewayServerProperties.getInitialCapacityOfServerCache())
                        .expireAfterWrite(apiGatewayServerProperties.getServerCacheAutoExpirationInSeconds(), TimeUnit.SECONDS)
                        .removalListener(new RemovalListener<String, Value>() {
                            @Override
                            public void onRemoval(RemovalNotification<String, Value> notification) {
                                //此处不删除readOnlyCacheMap缓存当心跳时删除
                                String removedKey = notification.getKey();
                            }
                        })
                        .build(new CacheLoader<String, Value>() {
                            @Override
                            public Value load(String key) throws Exception {
                                AppServer value = generatePayload(key);
                                return new Value(value);
                            }
                        });

        if (shouldUseReadOnlyServerCache) {
            appServerExecutorService.scheduleAtFixedRate(getCacheUpdateTask(),
                    serverCacheUpdateIntervalMs, serverCacheUpdateIntervalMs, TimeUnit.MILLISECONDS);
        }

    }

    /*
     * Generate pay load for the given key.
     */
    private AppServer generatePayload(String key) {
        GwAppInfo gwAppInfo = gwAppInfoMapper.selectByAppkey(key);
        if (Objects.nonNull(gwAppInfo)) {
            AppServer appServer = BeanCopierUtils.copierTargetBean(gwAppInfo, GwAppInfo.class, AppServer.class);
            Set<GwAppServer> gwAppServers = gwAppServerMapper.selectListByAppkey(key);
            if (!CollectionUtils.isEmpty(gwAppServers)) {
                Set<AppServer.Server> servers = BeanCopierUtils.copierTargetBeanSet(gwAppServers, GwAppServer.class, AppServer.Server.class);
                appServer.setServers(servers);
            }
            return appServer;
        }
        return null;
    }

    /**
     * 更新缓存
     *
     * @return
     */
    private Runnable getCacheUpdateTask() {
        return () -> {
            log.debug("Updating the client cache from server cache");
            for (String key : readOnlyCacheMap.keySet()) {
                if (log.isDebugEnabled()) {
                    log.debug("Updating the client cache from server cache for appid : {} ",
                            key);
                }
                try {
                    Value value = readWriteCacheMap.get(key);
                    AppServer cacheValue = value.getAppServer();
                    AppServer currentCacheValue = readOnlyCacheMap.get(key);
                    if (cacheValue != currentCacheValue) {
                        readOnlyCacheMap.put(key, cacheValue);
                    }
                } catch (Throwable th) {
                    log.error("Error while updating the client cache from server cache for key {}", key, th);
                }
            }
        };
    }

    /**
     * 获取应用服务信息
     *
     * @param appId
     * @return
     */
    @Override
    public AppServer get(String appId) {
        AppServer payload = null;
        Value value = null;
        try {
            if (shouldUseReadOnlyServerCache) {
                final AppServer currentPayload = readOnlyCacheMap.get(appId);
                if (currentPayload != null) {
                    payload = currentPayload;
                } else {
                    value = readWriteCacheMap.get(appId);
                    payload = value.getAppServer();
                    if (payload != null) {
                        readOnlyCacheMap.put(appId, payload);
                    }
                }
            } else {
                value = readWriteCacheMap.get(appId);
                payload = value.getAppServer();
            }
        } catch (Throwable t) {
            log.error("Cannot get value for key : {}", appId, t);
        }
        return payload;
    }

    /**
     * The class that stores payload in both compressed and uncompressed form.
     */
    public class Value {
        private AppServer appServer;

        public Value(AppServer appServer) {
            this.appServer = appServer;
        }

        public AppServer getAppServer() {
            return appServer;
        }

        public void setAppServer(AppServer appServer) {
            this.appServer = appServer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Value value = (Value) o;
            return Objects.equals(appServer, value.appServer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(appServer);
        }
    }
}
