package com.example.commerce.common.exception.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.commerce.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class ErrorResponse {

    private final boolean success = false;
    private final int status;
    private final String code;
    private final String message;
    private final LocalDateTime timeStamp;
    private final List<FieldError> errors;

    private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.timeStamp = LocalDateTime.now();
        this.errors = errors;
    }

    private ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.timeStamp = LocalDateTime.now();
        this.errors = new ArrayList<>();
    }

    /*
    기본 생성자 대신 정적 팩토리 메소드 of
     */

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final List<FieldError> errors) {
        return new ErrorResponse(code, errors);
    }

}