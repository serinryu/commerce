package com.example.commerce.domains.cart.domain;

import com.example.commerce.domains.item.exception.NotEnoughStockQuantityException;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CartEntityTest {
    private Long CART_ID = 1l; // 장바구니 ID 고정
    private Long CART_ITEM_ID = 1l; // 장바구니 안의 상품명 고정


    @Test
    public void addCartTest_새장바구니생성() throws Exception {
        // given
        CartEntity cartEntity = createCartEntity();
        int ORDER_COUNT = 1;
        int TARGET_STOCK_QUANTITY = 100;
        CartLine cartLine = new CartLine(CART_ID, CART_ITEM_ID, ORDER_COUNT);

        // when
        cartEntity.addItemToCart(TARGET_STOCK_QUANTITY, cartLine);

        // then
        assertThat(cartEntity.getCartLines().get(CART_ITEM_ID).getOrderCount(), is(ORDER_COUNT));
    }

    @Test
    public void addCartTest_장바구니에_기존아이템이_있었을경우() throws Exception {
        // given
        CartEntity cartEntity = createCartEntity();
        int FIRST_ORDER_COUNT = 1;
        int TARGET_STOCK_QUANTITY = 100;
        cartEntity.addItemToCart(TARGET_STOCK_QUANTITY, new CartLine(CART_ID, CART_ITEM_ID, FIRST_ORDER_COUNT));

        // when
        int SECOND_ORDER_COUNT = 2;
        cartEntity.addItemToCart(TARGET_STOCK_QUANTITY, new CartLine(CART_ID, CART_ITEM_ID, SECOND_ORDER_COUNT));

        // then
        int TOTAL_ORDER_COUNT = FIRST_ORDER_COUNT + SECOND_ORDER_COUNT;
        assertThat(cartEntity.getCartLines().get(CART_ITEM_ID).getOrderCount(), is(TOTAL_ORDER_COUNT));
    }

    @Test
    public void addCartTest_재고부족시() throws Exception {
        // given
        CartEntity cartEntity = createCartEntity();
        int ORDER_COUNT = 2;
        int TARGET_STOCK_QUANTITY = 1;
        CartLine cartLine = new CartLine(CART_ID, CART_ITEM_ID, ORDER_COUNT);

        // when & then
        assertThrows(NotEnoughStockQuantityException.class, () -> {
            cartEntity.addItemToCart(TARGET_STOCK_QUANTITY, cartLine);
        });
    }

    @Test
    public void modifyOrderCountTest() throws Exception {
        // given
        CartEntity cartEntity = createCartEntity();
        int BEFORE_ORDER_COUNT = 1;
        int TARGET_STOCK_QUANTITY = 100;
        cartEntity.addItemToCart(TARGET_STOCK_QUANTITY, new CartLine(CART_ID, CART_ITEM_ID, BEFORE_ORDER_COUNT));

        // when
        int NEW_ORDER_COUNT = 2;
        cartEntity.modifyOrderCount(TARGET_STOCK_QUANTITY, new CartLine(CART_ID, CART_ITEM_ID, NEW_ORDER_COUNT));

        // then
        assertThat(cartEntity.getCartLines().get(CART_ITEM_ID).getOrderCount(), is(NEW_ORDER_COUNT));
    }

    @Test
    public void modifyOrderCountTest_재고부족시() throws Exception {
        // given
        CartEntity cartEntity = createCartEntity();
        int BEFORE_ORDER_COUNT = 1;
        int TARGET_STOCK_QUANTITY = 1;
        cartEntity.addItemToCart(TARGET_STOCK_QUANTITY, new CartLine(CART_ID, CART_ITEM_ID, BEFORE_ORDER_COUNT));

        int NEW_ORDER_COUNT = 2;
        CartLine modifiedCartLine = new CartLine(CART_ID, CART_ITEM_ID, NEW_ORDER_COUNT);

        // when & then
        assertThrows(NotEnoughStockQuantityException.class, () -> {
            cartEntity.modifyOrderCount(1, modifiedCartLine);
        });
    }

    @Test
    public void removeCartLineTest() throws Exception {
        // given
        int TARGET_STOCK_QUANTITY = 100;
        CartEntity cartEntity = createCartEntity();
        cartEntity.addItemToCart(TARGET_STOCK_QUANTITY, new CartLine(CART_ID, CART_ITEM_ID, 1));

        // when
        cartEntity.removeCartLine(CART_ITEM_ID);

        // then
        assertThat(cartEntity.getCartLines().containsKey(CART_ITEM_ID), is(false));
    }

    private CartEntity createCartEntity() {
        CartEntity cartEntity = new CartEntity(1l);
        ReflectionTestUtils.setField(cartEntity, "cartId", CART_ID); // private 변수인 cartId 설정
        return cartEntity;
    }
}
