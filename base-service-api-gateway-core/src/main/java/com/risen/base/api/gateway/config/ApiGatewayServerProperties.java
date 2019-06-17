package com.risen.base.api.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mengxr
 * @since 1.0
 */
@ConfigurationProperties("spring.cloud.gateway.server")
public class ApiGatewayServerProperties {
    /**
     * skip 授权服务校验
     */
    private Boolean skip = Boolean.FALSE;

    public Boolean getSkip() {
        return skip;
    }

    public void setSkip(Boolean skip) {
        this.skip = skip;
    }
}
