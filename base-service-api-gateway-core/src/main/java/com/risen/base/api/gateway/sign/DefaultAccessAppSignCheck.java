package com.risen.base.api.gateway.sign;

import com.risen.base.api.gateway.config.ApiGatewayAppProperties;
import com.risen.base.api.gateway.util.ServerHttpRequestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

/**
 * Default  impl {@link AccessAppSignCheck}
 *
 * @author mengxr
 * @since 1.0
 */
public class DefaultAccessAppSignCheck implements AccessAppSignCheck {
    private static final Log log = LogFactory.getLog(DefaultAccessAppSignCheck.class);

    private ApiGatewayAppProperties apiGatewayAppProperties;

    public DefaultAccessAppSignCheck(ApiGatewayAppProperties apiGatewayAppProperties) {
        this.apiGatewayAppProperties = apiGatewayAppProperties;
    }

    /**
     * check access
     *
     * @param request
     */
    @Override
    public void validAccessAppSign(ServerHttpRequest request) throws InvalidAccessTokenException {
        String appId = ServerHttpRequestUtils.getHttpHeaderParam(request, apiGatewayAppProperties.getAppId());
        if (StringUtils.isEmpty(appId)) {
            log.warn("appId not null");
            throw new InvalidAccessTokenException("appId not null");
        }
        String timeStamp = ServerHttpRequestUtils.getHttpHeaderParam(request, apiGatewayAppProperties.getTimestamp());
        if (StringUtils.isEmpty(appId)) {
            log.warn("timeStamp not null");
            throw new InvalidAccessTokenException("timeStamp not null");
        }
        String sign = ServerHttpRequestUtils.getHttpHeaderParam(request, apiGatewayAppProperties.getSign());
        if (StringUtils.isEmpty(sign)) {
            log.warn("sign not null");
            throw new InvalidAccessTokenException("sign not null");
        }
    }
}
