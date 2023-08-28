package com.example.commerce.domains.cart.presentation;

import com.example.commerce.common.exception.SuccessCode;
import com.example.commerce.common.exception.response.SuccessResponse;
import com.example.commerce.domains.cart.query.dto.CartLineDto;
import com.example.commerce.domains.cart.service.AddToCartRequestForm;
import com.example.commerce.domains.cart.service.CartService;
import com.example.commerce.domains.cart.service.ModifyOrderCountRequestForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 장바구니 상품 조회
    // 이후에는 SpringSecurity의 기능을 이용해, 현재 로그인 중인 사용자의 정보를 얻어와 사용 (Authentication 객체)
    @GetMapping(value = "/carts")
    public ResponseEntity<SuccessResponse<List<CartLineDto>>> getCartPage(@RequestParam Long memberId){
        List<CartLineDto> data = cartService.getCartInCartPage(memberId);
        return ResponseEntity
                .status(SuccessCode.GET_INFO_SUCCESS.getStatus())
                .body(SuccessResponse.of(SuccessCode.GET_INFO_SUCCESS, data));
    }

    // 장바구니에 아이템 추가 기능
    @PostMapping(value = "/carts")
    public ResponseEntity<SuccessResponse<List<CartLineDto>>> addItemToCart(@Valid AddToCartRequestForm addToCartRequestForm, @RequestParam Long memberId){
        cartService.addItemToCart(memberId, addToCartRequestForm);
        return ResponseEntity
                .status(SuccessCode.CREATE_SUCCESS.getStatus())
                .body(SuccessResponse.of(SuccessCode.CREATE_SUCCESS));
    }

    // 장바구니 수량 변경
    @PutMapping(value = "/carts")
    public ResponseEntity<SuccessResponse<List<CartLineDto>>> modifyOrderCount(@Valid ModifyOrderCountRequestForm modifyOrderCountRequestForm, @RequestParam Long memberId){
        cartService.modifyOrderCount(memberId, modifyOrderCountRequestForm);
        return ResponseEntity
                .status(SuccessCode.UPDATE_SUCCESS.getStatus())
                .body(SuccessResponse.of(SuccessCode.UPDATE_SUCCESS));
    }

    // 장바구니 안의 상품 삭제 기능 (1개의 아이템)
    @DeleteMapping(value = "/carts")
    public ResponseEntity<SuccessResponse<List<CartLineDto>>> removeCartLine(@RequestParam("itemId") Long itemId, @RequestParam Long memberId){
        cartService.removeCartLine(memberId, itemId);
        return ResponseEntity
                .status(SuccessCode.DELETE_SUCCESS.getStatus())
                .body(SuccessResponse.of(SuccessCode.DELETE_SUCCESS));
    }

}
