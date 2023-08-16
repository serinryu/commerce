package com.example.commerce.domains.order.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    private Long itemId; // 주문 상품의 id (OrderItem)
    private int orderCount; // 주문 상품의 갯수
}
