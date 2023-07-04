package com.rova.transactionservice.exceptions;

import com.rova.transactionservice.dto.ErrorResponseWithArgsDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomBindException extends Exception {

    private ErrorResponseWithArgsDto.ErrorWithArguments[] errorWithArguments;

    public CustomBindException(ErrorResponseWithArgsDto.ErrorWithArguments... errorWithArguments) {
        this.errorWithArguments = errorWithArguments;
    }

    public CustomBindException(String s, Object... args) {
        this.errorWithArguments = new ErrorResponseWithArgsDto.ErrorWithArguments[1];
        this.errorWithArguments[0] = new ErrorResponseWithArgsDto.ErrorWithArguments(s, args);
    }
}
