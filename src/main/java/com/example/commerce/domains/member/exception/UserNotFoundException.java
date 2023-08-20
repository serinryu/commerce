package com.example.commerce.domains.member.exception;

import com.example.commerce.common.exception.BusinessException;
import com.example.commerce.common.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public static final BusinessException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
