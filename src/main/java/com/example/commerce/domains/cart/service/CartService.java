package com.example.commerce.domains.cart.service;

import com.example.commerce.domains.cart.domain.CartEntity;
import com.example.commerce.domains.cart.domain.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;

    public Long createCart(Long memberId){
        return cartRepository.save(new CartEntity(memberId))
                .getCartId();
    }
}
