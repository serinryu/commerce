package com.example.commerce.domains.cart.presentation;

import com.example.commerce.domains.cart.query.dto.CartLineDto;
import com.example.commerce.domains.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 이후에는 SpringSecurity의 기능을 이용해, 현재 로그인 중인 사용자의 정보를 얻어와 사용 (Authentication 객체)
    @GetMapping(value = "/carts")
    public List<CartLineDto> getCartPage(@RequestParam Long memberId){
        List<CartLineDto> cartLineDtoList = cartService.getCartInCartPage(memberId);
        return cartLineDtoList;
    }

}
