package com.example.commerce.domains.member.exception;

import com.example.commerce.common.exception.CodeException;
import com.example.commerce.common.exception.ErrorCode;

public class AlreadySignUpUserException extends CodeException {

    public static final CodeException EXCEPTION = new AlreadySignUpUserException();

    public AlreadySignUpUserException(){
        super(ErrorCode.USER_ALREADY_SIGNUP); // CodeException(ErrorCode errorcode)
    }
}
