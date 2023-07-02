package com.rova.transactionservice.exceptions;

import com.rova.transactionservice.dto.ErrorResponseDto;
import com.rova.transactionservice.dto.ErrorResponseWithArgsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalException(Exception e) {
        log.error("An unexpected exception was thrown :: ", e);
        return ErrorResponseDto.build(HttpStatus.INTERNAL_SERVER_ERROR, "something.went.wrong");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> emptyResultDataAccessException(NotFoundException e) {
        log.error("Empty result from data access :: ", e);
        return ErrorResponseDto.build(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<?> emptyResultDataAccessException(InvalidDataAccessApiUsageException e) {
        log.error("Empty result from data access :: ", e);
        return ErrorResponseDto.build(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<?> unsupportedOperationException(UnsupportedOperationException e) {
        return ErrorResponseDto.build(HttpStatus.INTERNAL_SERVER_ERROR, "unsupported.operation");
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable( HttpMessageNotWritableException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return ErrorResponseDto.build(HttpStatus.INTERNAL_SERVER_ERROR, "something.went.wrong");
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,  HttpStatus status,
                                                                  WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return ErrorResponseDto.build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return handleRequestBodyException(ex.getFieldErrors());
    }

    @Override
    protected ResponseEntity<Object> handleBindException( BindException ex,
                                                          HttpHeaders headers, HttpStatus status,
                                                          WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return handleRequestBodyException(ex.getFieldErrors());
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return ErrorResponseDto.build(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(@NonNull MissingServletRequestPartException ex,
                                                                     @NonNull HttpHeaders headers,
                                                                     @NonNull HttpStatus status,
                                                                     @NonNull WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return ErrorResponseWithArgsDto.build(status,
                new ErrorResponseWithArgsDto.ErrorWithArguments("missing.request.part",
                        new Object[]{ex.getRequestPartName()}));
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(@NonNull MissingPathVariableException ex,
                                                               @NonNull HttpHeaders headers,
                                                               @NonNull HttpStatus status,
                                                               @NonNull WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return ErrorResponseWithArgsDto.build(status,
                new ErrorResponseWithArgsDto.ErrorWithArguments("missing.request.path.variable",
                        new Object[]{ex.getVariableName()}));
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(@NonNull MissingServletRequestParameterException ex,
                                                                          @NonNull HttpHeaders headers, @NonNull HttpStatus status,
                                                                          @NonNull WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return ErrorResponseWithArgsDto.build(status,
                new ErrorResponseWithArgsDto.ErrorWithArguments("missing.request.param",
                        new Object[]{ex.getParameterName()}));
    }

    public ResponseEntity<Object> handleRequestBodyException(List<FieldError> errors) {
        return ErrorResponseWithArgsDto.build(HttpStatus.BAD_REQUEST, errors);
    }
}
