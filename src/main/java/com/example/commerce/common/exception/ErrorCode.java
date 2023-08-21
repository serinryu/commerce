package com.example.commerce.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorCode {

    // GLOBAL
    INTERNAL_SERVER_ERROR(500, "GLOBAL_500_1", "서버 오류. 관리자에게 문의 부탁드립니다."),
    METHOD_NOT_ALLOWED(405, "GLOBAL_405_1", "허용되지 않는 HTTP 메소드입니다."),
    INVALID_INPUT_VALUE(400, "GLOBAL_400_1", "Invalid input type"),
    INVALID_TYPE_VALUE(400, "GLOBAL_400_2", "Invalid type value"),
    BAD_REQUEST(400, "400", "Bad Request"),

    // User
    USER_ALREADY_SIGNUP(400, "USER_400_1", "이미 회원가입한 유저입니다."),
    USER_NOT_FOUND(404, "USER_404_1", "유저 정보를 찾을 수 없습니다."),

    // Item
    ITEM_NOT_FOUND(404, "ITEM_404_1", "상품 정보를 찾을 수 없습니다."),
    NO_ITEM_STOCK_LEFT(400, "ITEM_400_1", "상품의 재고가 없습니다.");

    private final int status;
    private final String code;
    private final String message;

}
