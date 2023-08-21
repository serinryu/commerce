package com.example.commerce.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    GET_INFO_SUCCESS(200, "E001", "retrieved successfully"),
    CREATE_SUCCESS(201, "E002", "created successfully"),
    UPDATE_SUCCESS(200, "E003", "updated successfully"),
    DELETE_SUCCESS(200, "E004", "deleted successfully");

    private final int status;
    private final String code;
    private final String message;

}
