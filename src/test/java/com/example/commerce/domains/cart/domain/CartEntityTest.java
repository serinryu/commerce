package com.example.commerce.domains.cart.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
public class CartEntityTest {

    private Long CART_ID = 1l;
    private Long CART_ITEM_ID = 1l;

    @Test
    public void addCartTest() throws Exception {
        // given
        CartEntity cartEntity = createCartEntity();
        int ORDER_COUNT = 1;
        CartLine cartLine = new CartLine(CART_ID, CART_ITEM_ID, ORDER_COUNT);

        // when
        cartEntity.addItemToCart(cartLine);

        // then
        assertThat(cartEntity.getCartLines().get(CART_ITEM_ID).getOrderCount(), is(ORDER_COUNT));
    }

    private CartEntity createCartEntity() {
        CartEntity cartEntity = new CartEntity(1l);
        ReflectionTestUtils.setField(cartEntity, "cartId", CART_ID); // private 변수인 cartId 설정
        return cartEntity;
    }
}
