package com.example.commerce.common.exception.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.commerce.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class ErrorResponse extends Response{

    private final List<FieldError> errors;

    private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
        super(false, errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage(), LocalDateTime.now());
        this.errors = errors;
    }

    private ErrorResponse(ErrorCode errorCode) {
        super(false, errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage(), LocalDateTime.now());
        this.errors = new ArrayList<>();
    }

    /*
    기본 생성자 대신 정적 팩토리 메소드 of : 입력 매개변수에 따라 유연하게 ErrorResponse 객체를 반환하므로써 다양한 예외처리에 대응
     */

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final List<FieldError> errors) {
        return new ErrorResponse(code, errors);
    }



}