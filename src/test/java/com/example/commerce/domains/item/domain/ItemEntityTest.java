package com.example.commerce.domains.item.domain;

import com.example.commerce.domains.item.exception.NotEnoughStockQuantityException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemEntityTest {
    @Test
    public void removeStockQuantityTest() {
        //given
        int STOCK_QUANTITY = 10;
        ItemEntity itemEntity = createItemEntity(STOCK_QUANTITY);

        //when
        int ORDER_QUANTITY = 2;
        itemEntity.removeStockQuantity(ORDER_QUANTITY);

        //then
        assertEquals(itemEntity.getStockQuantity(), (STOCK_QUANTITY - ORDER_QUANTITY));
    }

    @Test
    public void NotEnoughStockQuantityExceptionTest() {
        //given
        int STOCK_QUANTITY = 10;
        ItemEntity itemEntity = createItemEntity(STOCK_QUANTITY);

        //when, then
        int OVER_ORDER_QUANTITY = 20;
        assertThrows(NotEnoughStockQuantityException.class,
                () -> itemEntity.removeStockQuantity(OVER_ORDER_QUANTITY));
    }

    @Test
    public void addStockQuantityTest() {
        //given
        int STOCK_QUANTITY = 10;
        ItemEntity itemEntity = createItemEntity(STOCK_QUANTITY);

        //when
        int ADD_STOCK_QUANTITY = 10;
        itemEntity.addStockQuantity(ADD_STOCK_QUANTITY);

        //then
        assertEquals(itemEntity.getStockQuantity(), (STOCK_QUANTITY + ADD_STOCK_QUANTITY));
    }

    private ItemEntity createItemEntity(int stockQuantity) {
        return ItemEntity.builder()
                .categoryId(1L)
                .imagePath("TEST")
                .name("TEST")
                .price(1000)
                .stockQuantity(stockQuantity)
                .build();
    }
}
