package com.example.commerce.domains.cart.presentation;

import com.example.commerce.common.exception.SuccessCode;
import com.example.commerce.domains.cart.query.dto.CartLineDto;
import com.example.commerce.domains.cart.service.AddToCartRequestForm;
import com.example.commerce.domains.cart.service.CartService;
import com.example.commerce.domains.cart.service.ModifyOrderCountRequestForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    @Test
    public void testGetCartPage() throws Exception {
        Long memberId = 1L;
        List<CartLineDto> cartLineList = createCartLineList();

        when(cartService.getCartInCartPage(memberId)).thenReturn(cartLineList);

        mockMvc.perform(get("/carts")
                        .param("memberId", memberId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray());

        verify(cartService, times(1)).getCartInCartPage(memberId);
    }

    @Test
    public void testAddItemToCart() throws Exception {
        Long memberId = 1L;
        AddToCartRequestForm addToCartRequestForm = createCartRequestForm();
        // Initialize addToCartRequestForm fields

        mockMvc.perform(post("/carts")
                        .param("memberId", memberId.toString())
                        .content(objectMapper.writeValueAsString(addToCartRequestForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(0));

        verify(cartService, times(1)).addItemToCart(eq(memberId), any());
    }

    @Test
    public void testModifyOrderCount() throws Exception {
        Long memberId = 1L;
        ModifyOrderCountRequestForm modifyOrderCountRequestForm = createModifyForm();
        // Initialize modifyOrderCountRequestForm fields

        mockMvc.perform(put("/carts")
                        .param("memberId", memberId.toString())
                        .content(objectMapper.writeValueAsString(modifyOrderCountRequestForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(cartService, times(1)).modifyOrderCount(eq(memberId), any());
    }

    @Test
    public void testRemoveCartLine() throws Exception {
        Long memberId = 1L;
        Long itemId = 1L;

        mockMvc.perform(delete("/carts")
                        .param("memberId", memberId.toString())
                        .param("itemId", itemId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(cartService, times(1)).removeCartLine(eq(memberId), eq(itemId));
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

    private AddToCartRequestForm createCartRequestForm(){
        return new AddToCartRequestForm(1L, 1);
    }

    private ModifyOrderCountRequestForm createModifyForm(){
        return new ModifyOrderCountRequestForm(1L, 1);
    }

}
