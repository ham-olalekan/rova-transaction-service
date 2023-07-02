package com.rova.transactionservice.config;

import com.rova.transactionservice.services.impl.IdempotentServiceImpl;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CachingConfig {
    private static final int TTL = 120;

    @Bean
    public IdempotentServiceImpl IdempotentServiceImplCache() {
        return new IdempotentServiceImpl(TTL, TimeUnit.SECONDS);
    }
}
