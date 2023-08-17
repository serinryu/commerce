package com.example.commerce.common.exception;

import com.example.commerce.common.exception.response.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*
    Member
     */

    // 회원가입시에 이미 회원가입한 유저일시 발생하는 오류
    USER_ALREADY_SIGNUP(400, "USER_400_1", "이미 회원가입한 유저입니다."),
    // 유저 정보를 찾을 수 없는 경우
    USER_NOT_FOUND(404, "USER_404_1", "유저 정보를 찾을 수 없습니다.");

    private Integer status;
    private String code;
    private String reason;

    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }

}
