package com.rova.transactionservice.exceptions;

import lombok.Getter;

@Getter
public class DuplicateRequestException extends Exception{
    private String message;

    public DuplicateRequestException(String message) {
        this.message = message;
    }
}
