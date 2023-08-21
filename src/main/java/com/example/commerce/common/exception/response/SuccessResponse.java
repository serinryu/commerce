package com.example.commerce.common.exception.response;

import java.time.LocalDateTime;

import com.example.commerce.common.exception.SuccessCode;
import lombok.Getter;

@Getter
public class SuccessResponse<T> {

    private final boolean success = true;
    private final int status;
    private final String code;
    private final String message;
    private final LocalDateTime timeStamp;
    private final T data;

    private SuccessResponse(SuccessCode successCode, T data) {
        this.status = successCode.getStatus();
        this.code = successCode.getCode();
        this.message = successCode.getMessage();
        this.data = data;
        this.timeStamp = LocalDateTime.now();
    }

    /*
    기본 생성자 대신 정적 팩토리 메소드 of
     */
    public static <T> SuccessResponse<T> of(SuccessCode successCode, T data){
        return new SuccessResponse<>(successCode, data);
    }
}