package com.example.commerce.domains.cart.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable //임베디드 타입
@Table(name = "cart_line")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CartLine {
    private Long cartId;
    private Long itemId; // 상품 ID
    private Integer orderCount; // 상품 주문수량
}