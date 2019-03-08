package com.risen.base.api.gateway.cache;

/**
 * App Server 存储
 *
 * @author mengxr
 * @since 1.0
 */
public interface AppServerStorage {
    /**
     * 生成AppServer
     * @param appId
     * @return
     */
    AppServer generateAppServer(String appId);
}
