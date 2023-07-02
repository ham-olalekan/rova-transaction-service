package com.rova.transactionservice.services;

import com.rova.transactionservice.dto.IdempotentDto;

public interface IdempotencyService {
    void lock(String key);
    void release(String key);

    String get(String key);
}
