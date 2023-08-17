package com.example.commerce.common.exception;

import com.example.commerce.common.exception.response.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Member
    USER_ALREADY_SIGNUP(400, "USER_400_1", "이미 회원가입한 유저입니다.");

    private Integer status;
    private String code;
    private String reason;

    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }

}
