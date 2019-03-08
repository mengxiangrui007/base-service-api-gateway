package com.risen.base.api.gateway.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

/**
 * @author mengxr
 * @since 1.0
 */
public class ServerHttpRequestUtils {

    /**
     * get http Header
     *
     * @param serverHttpRequest
     * @param key
     * @return
     */
    public static String getHttpHeaderParam(ServerHttpRequest serverHttpRequest, String key) {
        HttpHeaders httpHeaders = serverHttpRequest.getHeaders();
        String param = httpHeaders.getFirst(key);
        if (StringUtils.isEmpty(param)) {
            param = serverHttpRequest.getQueryParams().getFirst(key);
        }
        return param;
    }
}
