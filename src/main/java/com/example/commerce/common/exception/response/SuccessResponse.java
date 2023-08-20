package com.example.commerce.common.exception.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SuccessResponse {

    private final boolean success = true;
    private final int status;
    private final Object data;
    private final LocalDateTime timeStamp;

    private SuccessResponse(int status, Object data) {
        this.status = status;
        this.data = data;
        this.timeStamp = LocalDateTime.now();
    }

    /*
    기본 생성자 대신 정적 팩토리 메소드 of
     */
    public static SuccessResponse of(final int status, final Object data){
        return new SuccessResponse(status, data);
    }
}