package com.rova.transactionservice.services;

public interface ILockService {
    boolean acquireLock(String lockKey, long expirationTime);

    void releaseLock(String lockKey);
}
