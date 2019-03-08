package com.risen.base.api.gateway.storage.db.config;

import com.risen.base.api.gateway.cache.AppServerStorage;
import com.risen.base.api.gateway.storage.db.DbAppServerStorage;
import com.risen.base.api.gateway.storage.db.mapper.GwAppInfoMapper;
import com.risen.base.api.gateway.storage.db.mapper.GwAppServerMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * api gateway storage
 *
 * @author mengxr
 * @since 1.0
 */
@Configuration
@ConditionalOnClass(AppServerStorage.class)
public class ApiGatewayStorageAutoConfiguration {

    @Bean
    public AppServerStorage appServerStorage(GwAppInfoMapper gwAppInfoMapper
            , GwAppServerMapper gwAppServerMapper) {
        return new DbAppServerStorage(gwAppInfoMapper, gwAppServerMapper);
    }

}
