package com.risen.base.api.gateway.server;

import com.risen.base.api.gateway.cache.AppServer;
import com.risen.base.api.gateway.cache.AppServerCache;
import com.risen.base.api.gateway.config.ApiGatewayAppProperties;
import com.risen.base.api.gateway.util.ServerHttpRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * Default impl {@link AppServerCache}
 *
 * @author mengxr
 * @since 1.0
 */
public class DefaultAppServerCheck implements AppServerCheck {

    private static final Logger log = LoggerFactory.getLogger(DefaultAppServerCheck.class);

    private ApiGatewayAppProperties apiGatewayAppProperties;

    private AppServerCache appServerCache;

    public DefaultAppServerCheck(ApiGatewayAppProperties apiGatewayAppProperties, AppServerCache appServerCache) {
        this.apiGatewayAppProperties = apiGatewayAppProperties;
        this.appServerCache = appServerCache;
    }

    /**
     * check server
     *
     * @param request
     */
    @Override
    public void validAccessServer(ServerHttpRequest request) throws InvalidAccessServerException {
        String appId = ServerHttpRequestUtils.getHttpHeaderParam(request, apiGatewayAppProperties.getAppId());
        if (StringUtils.isEmpty(appId)) {
            log.warn("appId not null");
            throw new InvalidAccessServerException("appId not null");
        }
        AppServer appServer = appServerCache.get(appId);
        if (appServer == null) {
            log.warn("未识别的AppID[{}]", appId);
            throw new InvalidAccessServerException("未识别的AppID [" + appId + "]");
        }
        Set<AppServer.Server> servers = appServer.getServers();
        if (CollectionUtils.isEmpty(servers)) {
            log.warn("当前AppID[{}]未配置授权服务列表", appId);
            throw new InvalidAccessServerException("当前AppID[" + appId + "]未配置授权服务列表");
        }

    }
}
