package com.example.commerce.common.exception;

import com.example.commerce.common.exception.response.ErrorResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static com.example.commerce.common.exception.ErrorCode.*;

// ErrorResponse 사용
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    // 지원하지 않는 HTTP 요청 메서드에 대한 예외 처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity
                .status(METHOD_NOT_ALLOWED.getStatus())
                .body(ErrorResponse.of(METHOD_NOT_ALLOWED));
    }

    // @Valid 에서 발생한 binding error 에 대한 예외 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .status(INVALID_TYPE_VALUE.getStatus())
                .body(ErrorResponse.of(INVALID_TYPE_VALUE));
    }

    // enum type 일치하지 않아서 발생하는 binding error 대한 예외 처리 (주로 @PathVariable 시 잘못된 type 데이터가 들어왔을 때 에러)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return ResponseEntity
            .status(INVALID_INPUT_VALUE.getStatus())
            .body(ErrorResponse.of(INVALID_INPUT_VALUE, fieldErrors));
    }

    // binding error 에 대한 예외 처리 (주로 MVC 에서 @ModelAttribute 에서 발생)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return ResponseEntity
            .status(INVALID_INPUT_VALUE.getStatus())
            .body(ErrorResponse.of(INVALID_INPUT_VALUE, fieldErrors));
    }

    // 비즈니스 요구사항에 따른 예외 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorResponse.of(e.getErrorCode(), e.getErrors()));
    }

    // 기타 예외처리 (그 밖에 발생하는 모든 예외처리가 이곳으로 모인다.)
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e){
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR.getStatus())
                .body(ErrorResponse.of(INTERNAL_SERVER_ERROR));
    }
}
