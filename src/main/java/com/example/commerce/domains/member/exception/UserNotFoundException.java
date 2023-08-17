package com.example.commerce.domains.member.exception;

import com.example.commerce.common.exception.CodeException;
import com.example.commerce.common.exception.ErrorCode;

public class UserNotFoundException extends CodeException {
    public static final CodeException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
