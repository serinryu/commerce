package com.example.commerce.common.exception;

import com.example.commerce.common.exception.response.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private ErrorCode errorCode;

    public ErrorReason getErrorReason() {
        return this.errorCode.getErrorReason();
    }
}