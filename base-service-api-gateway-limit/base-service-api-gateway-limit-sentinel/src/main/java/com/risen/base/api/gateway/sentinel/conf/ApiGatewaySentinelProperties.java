package com.risen.base.api.gateway.sentinel.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mengxr
 * @since 1.0
 */
@ConfigurationProperties("spring.cloud.gateway.sentinel")
public class ApiGatewaySentinelProperties {
    /**
     * 是否启用
     */
    private Boolean enabled = Boolean.TRUE;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
