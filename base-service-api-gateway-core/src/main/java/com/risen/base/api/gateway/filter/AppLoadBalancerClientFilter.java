package com.risen.base.api.gateway.filter;

import com.risen.base.api.gateway.util.GatewayServerWebExchangeUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.web.server.ServerWebExchange;

/**
 * 负载均衡
 *
 * @author mengxr
 * @since 1.0
 */
public class AppLoadBalancerClientFilter extends LoadBalancerClientFilter {
    public AppLoadBalancerClientFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {
        ServiceInstance choose = super.choose(exchange);
        if (choose != null) {
            exchange.getAttributes().put(GatewayServerWebExchangeUtils.GATEWAY_REQUEST_SERVICE_INSTANCE, choose);
        }
        return choose;
    }
}
