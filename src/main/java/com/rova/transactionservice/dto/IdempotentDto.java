package com.rova.transactionservice.dto;

import com.rova.transactionservice.enums.IdempotentAction;

public interface IdempotentDto {

    String getHash(long userId, IdempotentAction action);
}
