package com.rova.transactionservice.services.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.rova.transactionservice.services.IdempotencyService;

import java.util.concurrent.TimeUnit;

public class IdempotentServiceImpl implements IdempotencyService {

    private Cache<String, String> tokenCache;

    public IdempotentServiceImpl(int expiryDuration, TimeUnit timeUnit) {
        tokenCache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration, timeUnit)
                .build();
    }

    @Override
    public void lock(String key) {
        save(key, key);
    }

    @Override
    public void release(String key) {
        delete(key);
    }

    @Override
    public String get(String key) {
        return tokenCache.getIfPresent(key);
    }

    private void save(String key, String value) {
        if (key != null && value != null) {
            tokenCache.put(key, value);
        }
    }

    private void delete(String key) {
        tokenCache.invalidate(key);
    }
}
