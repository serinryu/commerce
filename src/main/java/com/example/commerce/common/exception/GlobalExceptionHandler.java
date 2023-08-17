package com.example.commerce.common.exception;

import com.example.commerce.common.exception.response.ErrorReason;
import com.example.commerce.common.exception.response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// ErrorResponse 사용
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CodeException.class)
    public ResponseEntity<ErrorResponse> CodeExceptionHandler(
            CodeException e, HttpServletRequest request) {
        ErrorCode code = e.getErrorCode();
        ErrorReason errorReason = code.getErrorReason();
        ErrorResponse errorResponse =
                new ErrorResponse(errorReason, request.getRequestURL().toString());
        return ResponseEntity
                .status(HttpStatus.valueOf(errorReason.getStatus()))
                .body(errorResponse);
    }
}
