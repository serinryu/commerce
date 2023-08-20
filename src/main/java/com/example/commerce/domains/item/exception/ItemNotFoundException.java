package com.example.commerce.domains.item.exception;

import com.example.commerce.common.exception.BusinessException;
import com.example.commerce.common.exception.ErrorCode;

public class ItemNotFoundException extends BusinessException {
    public final static BusinessException EXCEPTION = new ItemNotFoundException();
    private ItemNotFoundException(){
        super(ErrorCode.ITEM_NOT_FOUND);
    }
}
