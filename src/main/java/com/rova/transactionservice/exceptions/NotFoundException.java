package com.rova.transactionservice.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends Exception{
    private String message;

    public NotFoundException(String message) {
        this.message = message;
    }
}
