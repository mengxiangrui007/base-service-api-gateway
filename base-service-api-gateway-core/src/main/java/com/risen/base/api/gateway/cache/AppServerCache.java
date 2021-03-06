package com.risen.base.api.gateway.cache;

/**
 * app server cache
 *
 * @author mengxr
 * @since 1.0
 */
public interface AppServerCache {
    /**
     * 获取应用服务信息
     *
     * @param appId
     * @return
     */
    AppServer get(String appId);

}
