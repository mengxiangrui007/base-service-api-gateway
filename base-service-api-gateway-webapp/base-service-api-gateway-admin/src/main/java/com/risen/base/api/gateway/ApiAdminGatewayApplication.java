package com.risen.base.api.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.risen"})
@MapperScan(value = "com.risen.base.api.gateway.storage.db.mapper")
public class ApiAdminGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiAdminGatewayApplication.class, args);
    }

}