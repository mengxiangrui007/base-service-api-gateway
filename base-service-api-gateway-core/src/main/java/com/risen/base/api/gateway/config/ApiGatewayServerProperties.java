package com.risen.base.api.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mengxr
 * @since 1.0
 */
@ConfigurationProperties("spring.cloud.gateway.server")
public class ApiGatewayServerProperties {
    /**
     * 默认开启二级缓存
     */
    private boolean shouldUseReadOnlyServerCache = Boolean.TRUE;

    /**
     * 服务列表缓存自动过期时间
     */
    private Long serverCacheAutoExpirationInSeconds = 180L;
    /**
     * 初始化服务列表缓存容量
     */
    private Integer initialCapacityOfServerCache = 1000;
    /**
     * 缓存更新时间 默认30秒
     */
    private Long serverCacheUpdateIntervalMs = 30 * 1000L;

    public Long getServerCacheAutoExpirationInSeconds() {
        return serverCacheAutoExpirationInSeconds;
    }

    public void setServerCacheAutoExpirationInSeconds(Long serverCacheAutoExpirationInSeconds) {
        this.serverCacheAutoExpirationInSeconds = serverCacheAutoExpirationInSeconds;
    }

    public Integer getInitialCapacityOfServerCache() {
        return initialCapacityOfServerCache;
    }

    public void setInitialCapacityOfServerCache(Integer initialCapacityOfServerCache) {
        this.initialCapacityOfServerCache = initialCapacityOfServerCache;
    }

    public boolean getShouldUseReadOnlyServerCache() {
        return shouldUseReadOnlyServerCache;
    }

    public void setShouldUseReadOnlyServerCache(boolean shouldUseReadOnlyServerCache) {
        this.shouldUseReadOnlyServerCache = shouldUseReadOnlyServerCache;
    }

    public boolean isShouldUseReadOnlyServerCache() {
        return shouldUseReadOnlyServerCache;
    }

    public Long getServerCacheUpdateIntervalMs() {
        return serverCacheUpdateIntervalMs;
    }

    public void setServerCacheUpdateIntervalMs(Long serverCacheUpdateIntervalMs) {
        this.serverCacheUpdateIntervalMs = serverCacheUpdateIntervalMs;
    }
}
