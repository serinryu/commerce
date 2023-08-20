package com.example.commerce.common.exception;

import com.example.commerce.common.exception.response.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.commerce.common.value.Const.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // GLOBAL
    INTERNAL_SERVER_ERROR(INTERNAL_SERVER, "GLOBAL_500_1", "서버 오류. 관리자에게 문의 부탁드립니다."),

    // User
    USER_ALREADY_SIGNUP(BAD_REQUEST, "USER_400_1", "이미 회원가입한 유저입니다."),
    USER_NOT_FOUND(NOT_FOUND, "USER_404_1", "유저 정보를 찾을 수 없습니다."),

    // Item
    ITEM_NOT_FOUND(NOT_FOUND, "ITEM_404_1", "상품 정보를 찾을 수 없습니다."),
    NO_ITEM_STOCK_LEFT(BAD_REQUEST, "ITEM_400_1", "상품의 재고가 없습니다.");

    private Integer status;
    private String code;
    private String reason;

    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }

}
