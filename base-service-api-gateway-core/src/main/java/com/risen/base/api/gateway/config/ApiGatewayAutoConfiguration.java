package com.risen.base.api.gateway.config;

import com.risen.base.api.gateway.filter.AppkeySecretFilter;
import com.risen.base.api.gateway.mapper.GwAppInfoMapper;
import com.risen.base.api.gateway.mapper.GwAppServerMapper;
import com.risen.base.api.gateway.cache.AppServerCache;
import com.risen.base.api.gateway.cache.DefaultAppServerCache;
import com.risen.base.api.gateway.sign.AccessAppSignCheck;
import com.risen.base.api.gateway.sign.DefaultAccessAppSignCheck;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
public class ApiGatewayAutoConfiguration {

    @ConditionalOnProperty(name = "spring.cloud.gateway.app.skip", havingValue = "false", matchIfMissing = true)
    @Configuration
    @EnableConfigurationProperties({ApiGatewayAppProperties.class, ApiGatewayServerProperties.class})
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

        @Bean
        @ConditionalOnMissingBean
        public AppServerCache appServerCache(ApiGatewayServerProperties apiGatewayServerProperties, GwAppInfoMapper gwAppInfoMapper
                , GwAppServerMapper gwAppServerMapper) {
            return new DefaultAppServerCache(apiGatewayServerProperties, gwAppInfoMapper, gwAppServerMapper);
        }
    }


}
