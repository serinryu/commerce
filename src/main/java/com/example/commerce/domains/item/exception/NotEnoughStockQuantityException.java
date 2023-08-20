package com.example.commerce.domains.item.exception;

import com.example.commerce.common.exception.BusinessException;
import com.example.commerce.common.exception.ErrorCode;

public class NotEnoughStockQuantityException extends BusinessException {
    public final static BusinessException EXCEPTION = new NotEnoughStockQuantityException();
    private NotEnoughStockQuantityException(){
        super(ErrorCode.NO_ITEM_STOCK_LEFT);
    }
}