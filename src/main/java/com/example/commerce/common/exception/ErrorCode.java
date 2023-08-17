package com.example.commerce.common.exception;

import com.example.commerce.common.exception.response.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.commerce.common.value.Const.BAD_REQUEST;
import static com.example.commerce.common.value.Const.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 회원가입시에 이미 회원가입한 유저일시 발생하는 오류
    USER_ALREADY_SIGNUP(BAD_REQUEST, "USER_400_1", "이미 회원가입한 유저입니다."),
    // 유저 정보를 찾을 수 없는 경우
    USER_NOT_FOUND(NOT_FOUND, "USER_404_1", "유저 정보를 찾을 수 없습니다."),

    // 상품 정보를 찾을 수 없는 경우
    ITEM_NOT_FOUND(NOT_FOUND, "ITEM_404_1", "상품 정보를 찾을 수 없습니다."),
    // 상품의 재고가 다 떨어진 경우
    NO_ITEM_STOCK_LEFT(BAD_REQUEST, "ITEM_400_1", "상품의 재고가 없습니다.");

    private Integer status;
    private String code;
    private String reason;

    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }

}
