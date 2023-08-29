package com.example.commerce.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    GET_INFO_SUCCESS(200,  "retrieved successfully"),
    CREATE_SUCCESS(201, "created successfully"),
    UPDATE_SUCCESS(200, "updated successfully"),
    DELETE_SUCCESS(200, "deleted successfully");

    private final int status;
    private final String message;

}
