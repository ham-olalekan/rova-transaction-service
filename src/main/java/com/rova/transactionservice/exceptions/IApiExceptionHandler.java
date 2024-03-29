package com.rova.transactionservice.exceptions;

import com.rova.transactionservice.dto.ErrorResponseDto;
import com.rova.transactionservice.dto.ErrorResponseWithArgsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Nonnull;
import javax.validation.ValidationException;
import java.util.List;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class IApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalException(Exception e) {
        log.error("An unexpected exception was thrown :: ", e);
        return ErrorResponseDto.build(HttpStatus.INTERNAL_SERVER_ERROR, "something.went.wrong");
    }

    @ExceptionHandler(CommonsException.class)
    public ResponseEntity<Object> commonsException(CommonsException exception) {
        log.error("An expected exception was thrown :: ", exception);
        return ErrorResponseDto.build(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler(CustomBindException.class)
    public ResponseEntity<Object> customBindException(CustomBindException customBindException) {
        return ErrorResponseWithArgsDto.build(HttpStatus.BAD_REQUEST, customBindException.getErrorWithArguments());
    }

    @ExceptionHandler(CustomBindRuntimeException.class)
    public ResponseEntity<Object> customBindException(CustomBindRuntimeException customBindException) {
        return ErrorResponseWithArgsDto.build(HttpStatus.BAD_REQUEST, customBindException.getErrorWithArguments());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationException(ValidationException validationException) {
        if (validationException.getCause() instanceof CustomBindRuntimeException)
            return customBindException((CustomBindRuntimeException) validationException.getCause());
        return globalException(validationException);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<?> unsupportedOperationException(UnsupportedOperationException e) {
        return ErrorResponseDto.build(HttpStatus.INTERNAL_SERVER_ERROR, "unsupported.operation");
    }

    @Override
    @Nonnull
    protected ResponseEntity<Object> handleHttpMessageNotWritable(@Nonnull HttpMessageNotWritableException ex,
                                                                  @Nonnull HttpHeaders headers, @Nonnull HttpStatus status,
                                                                  @Nonnull WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return ErrorResponseDto.build(HttpStatus.INTERNAL_SERVER_ERROR, "something.went.wrong");
    }

    @Override
    @Nonnull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@Nonnull HttpMessageNotReadableException ex,
                                                                  @Nonnull HttpHeaders headers, @Nonnull HttpStatus status,
                                                                  @Nonnull WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return ErrorResponseDto.build(HttpStatus.BAD_REQUEST, "something.went.wrong.unreadable.body");
    }

    @Override
    @Nonnull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@Nonnull MethodArgumentNotValidException ex,
                                                                  @Nonnull HttpHeaders headers, @Nonnull HttpStatus status,
                                                                  @Nonnull WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return handleRequestBodyException(ex.getFieldErrors());
    }

    @Override
    @Nonnull
    protected ResponseEntity<Object> handleBindException(@Nonnull BindException ex,
                                                         @Nonnull HttpHeaders headers, @Nonnull HttpStatus status,
                                                         @Nonnull WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return handleRequestBodyException(ex.getFieldErrors());
    }

    public ResponseEntity<Object> handleRequestBodyException(List<FieldError> errors) {
        return ErrorResponseWithArgsDto.build(HttpStatus.BAD_REQUEST, errors);
    }
}
