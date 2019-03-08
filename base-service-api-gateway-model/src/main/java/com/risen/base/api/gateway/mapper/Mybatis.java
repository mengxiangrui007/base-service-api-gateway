package com.risen.base.api.gateway.mapper;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Mybatis Component
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Mybatis {

}
