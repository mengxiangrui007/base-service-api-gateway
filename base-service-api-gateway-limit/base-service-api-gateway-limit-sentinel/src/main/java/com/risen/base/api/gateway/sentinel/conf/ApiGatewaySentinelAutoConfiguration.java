package com.risen.base.api.gateway.sentinel.conf;

import com.risen.base.api.gateway.config.ApiGatewayAutoConfiguration;
import com.risen.base.api.gateway.sentinel.filter.SentinelFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.DispatcherHandler;

/**
 * Sentinel  config
 *
 * @author mengxr
 * @since 1.0
 */
@ConditionalOnProperty(name = "spring.cloud.gateway.sentinel.enabled", matchIfMissing = true)
@Configuration
@ConditionalOnClass(DispatcherHandler.class)
@EnableConfigurationProperties({ApiGatewaySentinelProperties.class})
@ConditionalOnBean(ApiGatewayAutoConfiguration.class)
public class ApiGatewaySentinelAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public SentinelFilter sentinelFilter() {
        return new SentinelFilter();
    }
}
