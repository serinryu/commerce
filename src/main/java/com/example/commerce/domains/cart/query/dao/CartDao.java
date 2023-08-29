package com.example.commerce.domains.cart.query.dao;

import com.example.commerce.domains.cart.query.dto.CartLineDto;

import java.util.List;

// DAO 인터페이스
public interface CartDao {
    // JPQL 을 통해서 DTO 을 반환하게 됨. (JpaCartDao 참고)
    List<CartLineDto> getCartLineListInCartPage(Long memberId);
}