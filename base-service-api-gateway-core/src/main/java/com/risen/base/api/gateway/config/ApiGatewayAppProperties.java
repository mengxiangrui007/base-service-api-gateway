package com.risen.base.api.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * app info
 *
 * @author mengxr
 * @since 1.0
 */
@ConfigurationProperties("spring.cloud.gateway.app")
public class ApiGatewayAppProperties {
    /**
     * appId name
     */
    private String appId = "X-GW-APPID";
    /**
     * Timestamp Name
     */
    private String timestamp = "X-GW-Timestamp";
    /**
     * Sign
     */
    private String sign = "X-GW-SIGN";
    /**
     * skip
     */
    private Boolean skip = Boolean.FALSE;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public Boolean getSkip() {
        return skip;
    }

    public void setSkip(Boolean skip) {
        this.skip = skip;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
