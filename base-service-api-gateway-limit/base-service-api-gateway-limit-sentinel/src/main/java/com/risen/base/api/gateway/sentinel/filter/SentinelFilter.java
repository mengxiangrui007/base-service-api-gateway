package com.risen.base.api.gateway.sentinel.filter;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.adapter.reactor.ContextConfig;
import com.alibaba.csp.sentinel.adapter.reactor.EntryConfig;
import com.alibaba.csp.sentinel.adapter.reactor.SentinelReactorTransformer;
import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.WebFluxCallbackManager;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author mengxr
 * @since 1.0
 */
public class SentinelFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return chain.filter(exchange)
                .transform(buildSentinelTransformer(exchange));
    }

    private SentinelReactorTransformer<Void> buildSentinelTransformer(ServerWebExchange exchange) {
        // Maybe we can get the URL pattern elsewhere via:
        // exchange.getAttributeOrDefault(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, path)

        String path = exchange.getRequest().getPath().value();
        String finalPath = Optional.ofNullable(WebFluxCallbackManager.getUrlCleaner())
                .map(f -> f.apply(exchange, path))
                .orElse(path);
        String origin = Optional.ofNullable(WebFluxCallbackManager.getRequestOriginParser())
                .map(f -> f.apply(exchange))
                .orElse(EMPTY_ORIGIN);

        return new SentinelReactorTransformer<>(
                new EntryConfig(finalPath, EntryType.IN, new ContextConfig(finalPath, origin)));
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private static final String EMPTY_ORIGIN = "";

}
