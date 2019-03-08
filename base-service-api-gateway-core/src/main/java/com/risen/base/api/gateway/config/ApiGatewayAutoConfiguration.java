package com.risen.base.api.gateway.config;

import com.risen.base.api.gateway.cache.AppServerCache;
import com.risen.base.api.gateway.cache.AppServerStorage;
import com.risen.base.api.gateway.cache.DefaultAppServerCache;
import com.risen.base.api.gateway.filter.AppLoadBalancerClientFilter;
import com.risen.base.api.gateway.filter.AppServerFilter;
import com.risen.base.api.gateway.filter.AppkeySecretFilter;
import com.risen.base.api.gateway.server.AppServerCheck;
import com.risen.base.api.gateway.server.DefaultAppServerCheck;
import com.risen.base.api.gateway.sign.AccessAppSignCheck;
import com.risen.base.api.gateway.sign.DefaultAccessAppSignCheck;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.DispatcherHandler;

/**
 * api gateway auto configuration
 *
 * @author mengxr
 * @since 1.0
 */
@ConditionalOnProperty(name = "spring.cloud.gateway.enabled", matchIfMissing = true)
@Configuration
@ConditionalOnClass(DispatcherHandler.class)
@EnableConfigurationProperties({ApiGatewayAppProperties.class, ApiGatewayServerProperties.class})
public class ApiGatewayAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AppServerCache appServerCache(ApiGatewayServerProperties apiGatewayServerProperties, AppServerStorage appServerStorage) {
        return new DefaultAppServerCache(apiGatewayServerProperties, appServerStorage);
    }


    @ConditionalOnProperty(name = "spring.cloud.gateway.app.skip", havingValue = "false", matchIfMissing = true)
    @Configuration
    public static class ApiGatewayAppkeySecretAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public AppkeySecretFilter appkeySecretFilter(AccessAppSignCheck accessAppSignCheck) {
            return new AppkeySecretFilter(accessAppSignCheck);
        }

        @Bean
        @ConditionalOnMissingBean
        public AccessAppSignCheck accessAppSignCheck(ApiGatewayAppProperties apiGatewayAppProperties, AppServerCache appServerCache) {
            return new DefaultAccessAppSignCheck(apiGatewayAppProperties, appServerCache);
        }
    }

    @ConditionalOnProperty(name = "spring.cloud.gateway.server.skip", havingValue = "false", matchIfMissing = true)
    @Configuration
    public static class ApiGatewayServerAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public AppServerFilter appServerFilter(AppServerCheck appServerCheck) {
            return new AppServerFilter(appServerCheck);
        }

        @Bean
        @ConditionalOnMissingBean
        public AppServerCheck appServerCheck(ApiGatewayAppProperties apiGatewayAppProperties
                , AppServerCache appServerCache) {
            return new DefaultAppServerCheck(apiGatewayAppProperties, appServerCache);
        }
    }

    @Configuration
    @AutoConfigureAfter(GatewayLoadBalancerClientAutoConfiguration.class)
    public static class ApiGatewayLoadBalancerClientAutoConfiguration {

        @Bean
        public LoadBalancerClientFilter loadBalancerClientFilter(LoadBalancerClient client, LoadBalancerProperties properties) {
            return new AppLoadBalancerClientFilter(client, properties);
        }
    }
}
