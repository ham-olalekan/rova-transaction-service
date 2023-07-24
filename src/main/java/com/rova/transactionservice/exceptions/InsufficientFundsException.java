package com.rova.transactionservice.exceptions;

import lombok.Getter;

@Getter
public class InsufficientFundsException extends Exception{
    private String message;

    public InsufficientFundsException(String message) {
        this.message = message;
    }
}