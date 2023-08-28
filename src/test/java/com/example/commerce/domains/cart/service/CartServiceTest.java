package com.example.commerce.domains.cart.service;

import com.example.commerce.domains.cart.domain.CartEntity;
import com.example.commerce.domains.cart.domain.CartRepository;
import com.example.commerce.domains.cart.query.dao.CartDao;
import com.example.commerce.domains.cart.query.dto.CartLineDto;
import com.example.commerce.domains.item.domain.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private CartService cartService;

    @Test
    public void testGetCartInCartPage() {
        // given
        Long memberId = 1L;
        List<CartLineDto> cartLineList = createCartLineList();
        when(cartDao.getCartLineListInCartPage(memberId)).thenReturn(cartLineList);

        // when
        List<CartLineDto> result = cartService.getCartInCartPage(memberId);

        // then
        assertEquals(result.get(0).getItemId(), 1L);
        assertEquals(result.get(1).getItemId(), 2L);
        verify(cartDao, times(1)).getCartLineListInCartPage(memberId);
    }

    @Test
    public void testCreateCartWhenCartExists() {
        // 이미 존재하는 장바구니일 경우, 기존의 장바구니 ID 를 리턴한다.
        // given
        Long memberId = 1L;
        CartEntity existingCart = new CartEntity(memberId);
        Long cartId = existingCart.getCartId();
        when(cartRepository.findFirstByMemberId(memberId)).thenReturn(Optional.of(existingCart));

        // when
        Long result = cartService.createCart(memberId);

        // then
        assertEquals(result, cartId);
        verify(cartRepository, times(1)).findFirstByMemberId(memberId);
        verify(cartRepository, never()).save(any());
    }

    @Test
    public void testCreateCartWhenCartDoesNotExist() {
        // 존재하지 않는 cart 의 경우 새롭게 생성한다.
        // given
        Long memberId = 1L;
        when(cartRepository.findFirstByMemberId(memberId)).thenReturn(Optional.empty());
        when(cartRepository.save(any())).thenReturn(new CartEntity(memberId));

        // then
        Long result = cartService.createCart(memberId);

        // when
        verify(cartRepository, times(1)).findFirstByMemberId(memberId);
        verify(cartRepository, times(1)).save(any());
    }

    ////// HELPER //////

    private List<CartLineDto> createCartLineList() {
        List<CartLineDto> cartLineList = new ArrayList<>();

        CartLineDto cartLine1 = CartLineDto.builder()
                .itemId(1L)
                .itemImagePath("item1.jpg")
                .itemName("Item 1")
                .itemPrice(100)
                .orderCount(2)
                .stockQuantity(10)
                .build();

        CartLineDto cartLine2 = CartLineDto.builder()
                .itemId(2L)
                .itemImagePath("item2.jpg")
                .itemName("Item 2")
                .itemPrice(150)
                .orderCount(1)
                .stockQuantity(5)
                .build();

        // Add more CartLineDto objects if needed
        cartLineList.add(cartLine1);
        cartLineList.add(cartLine2);

        return cartLineList;
    }


}