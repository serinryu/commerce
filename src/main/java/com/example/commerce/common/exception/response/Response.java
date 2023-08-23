package com.example.commerce.common.exception.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Response {
    private final boolean success;
    private final int status;
    private final String code;
    private final String message;
    private final LocalDateTime timeStamp;
}
