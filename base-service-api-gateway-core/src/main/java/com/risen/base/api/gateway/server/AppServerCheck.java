package com.risen.base.api.gateway.server;

import org.springframework.web.server.ServerWebExchange;

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
     * @param exchange
     */
    void validAccessServer(ServerWebExchange exchange) throws InvalidAccessServerException;
}
