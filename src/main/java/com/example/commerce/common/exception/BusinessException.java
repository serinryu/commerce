package com.example.commerce.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private ErrorCode errorCode;
    private List<FieldError> errors;

    public BusinessException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}