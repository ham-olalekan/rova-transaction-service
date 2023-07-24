package com.rova.transactionservice.services.impl;

import com.rova.transactionservice.services.ILockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedissonDistributedLockService implements ILockService {

    private final RedissonClient redissonClient;

    @Override
    public boolean acquireLock(String lockKey, long expirationTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(expirationTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void releaseLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }
}
