package com.risen.base.api.gateway.server;

import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * 校验当前应用下的服务授权
 *
 * @author mengxr
 * @since 1.0
 */
public interface AppServerCheck {
    /**
     * check server
     *
     * @param request
     */
    void validAccessServer(ServerHttpRequest request) throws InvalidAccessServerException;
}
