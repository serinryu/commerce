package com.example.commerce.domains.member.exception;

import com.example.commerce.common.exception.BusinessException;
import com.example.commerce.common.exception.ErrorCode;

public class AlreadySignUpUserException extends BusinessException {

    public static final BusinessException EXCEPTION = new AlreadySignUpUserException();

    public AlreadySignUpUserException(){
        super(ErrorCode.USER_ALREADY_SIGNUP); // BusinessException(ErrorCode errorcode)
    }
}
