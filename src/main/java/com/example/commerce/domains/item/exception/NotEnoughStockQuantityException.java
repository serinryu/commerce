package com.example.commerce.domains.item.exception;

import com.example.commerce.common.exception.CodeException;
import com.example.commerce.common.exception.ErrorCode;

public class NotEnoughStockQuantityException extends CodeException {
    public final static CodeException EXCEPTION = new NotEnoughStockQuantityException();
    private NotEnoughStockQuantityException(){
        super(ErrorCode.NO_ITEM_STOCK_LEFT);
    }
}