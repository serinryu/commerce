package com.example.commerce.common.exception.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final boolean success = false;
    private final int status;
    private final String code;
    private final String reason;
    private final LocalDateTime timeStamp;

    private final String path;

    private ErrorResponse(ErrorReason errorReason, String path) {
        this.status = errorReason.getStatus();
        this.code = errorReason.getCode();
        this.reason = errorReason.getReason();
        this.timeStamp = LocalDateTime.now();
        this.path = path;
    }

    private ErrorResponse(int status, String code, String reason, String path) {
        this.status = status;
        this.code = code;
        this.reason = reason;
        this.timeStamp = LocalDateTime.now();
        this.path = path;
    }

    /*
    기본 생성자 대신 정적 팩토리 메소드 of
     */

    public static ErrorResponse of(final ErrorReason errorReason, final String path){
        return new ErrorResponse(errorReason, path);
    }

    public static ErrorResponse of(final int status, final String code, final String reason, final String path){
        return new ErrorResponse(status, code, reason, path);
    }
}