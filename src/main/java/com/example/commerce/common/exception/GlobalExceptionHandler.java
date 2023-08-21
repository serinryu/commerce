package com.example.commerce.common.exception;

import com.example.commerce.common.exception.response.ErrorResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

// ErrorResponse 사용
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        ErrorCode methodNotAllowedError = ErrorCode.METHOD_NOT_ALLOWED;
        return ResponseEntity.
                status(methodNotAllowedError.getStatus())
                .body(ErrorResponse.of(methodNotAllowedError));
    }

    // @RequestParam, @PathVariable 등의 파라미터 타입 불일치 에러 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        ErrorCode invalidTypeValueError = ErrorCode.INVALID_TYPE_VALUE;
        return ResponseEntity
                .status(invalidTypeValueError.getStatus())
                .body(ErrorResponse.of(invalidTypeValueError));
    }

    // @Valid 를 통과하지 못한 요청 바디의 필드에 대한 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ErrorCode invalidInputValueError = ErrorCode.INVALID_INPUT_VALUE;
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return ResponseEntity
                .status(invalidInputValueError.getStatus())
                .body(ErrorResponse.of(invalidInputValueError, fieldErrors));
    }

    // 비즈니스 요구사항에 따른 예외 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleCodeException(BusinessException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorResponse.of(e.getErrorCode(), e.getErrors()));
    }

    // 기타 예외처리 (그 밖에 발생하는 모든 예외처리가 이곳으로 모인다.)
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e){
        ErrorCode internalServerError = ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(internalServerError.getStatus())
                .body(ErrorResponse.of(internalServerError));
    }
}
