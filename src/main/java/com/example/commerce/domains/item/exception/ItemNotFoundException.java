package com.example.commerce.domains.item.exception;

import com.example.commerce.common.exception.CodeException;
import com.example.commerce.common.exception.ErrorCode;

public class ItemNotFoundException extends CodeException {
    public final static CodeException EXCEPTION = new ItemNotFoundException();
    private ItemNotFoundException(){
        super(ErrorCode.ITEM_NOT_FOUND);
    }
}
