package com.example.commerce.common.exception.response;

import java.time.LocalDateTime;

import com.example.commerce.common.exception.SuccessCode;
import lombok.Getter;

@Getter
public class SuccessResponse<T> extends Response{

    private final T data;

    private SuccessResponse(SuccessCode successCode, T data) {
        // 성공 시 code 는 항상 0
        super(true, successCode.getStatus(), "0", successCode.getMessage(), LocalDateTime.now());
        this.data = data;
    }

    /*
    기본 생성자 대신 정적 팩토리 메소드 of
     */
    public static <T> SuccessResponse<T> of(SuccessCode successCode, T data){
        return new SuccessResponse<>(successCode, data);
    }
}