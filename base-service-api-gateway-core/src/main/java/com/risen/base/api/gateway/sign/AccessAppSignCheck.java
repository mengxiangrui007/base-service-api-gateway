package com.risen.base.api.gateway.sign;

import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * check sign
 *
 * @author mengxr
 * @since 1.0
 */
public interface AccessAppSignCheck {
    /**
     * check access
     *
     * @param request
     */
    void validAccessAppSign(ServerHttpRequest request) throws InvalidAccessTokenException;
}
