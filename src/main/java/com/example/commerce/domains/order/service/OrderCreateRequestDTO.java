package com.example.commerce.domains.order.service;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequestDTO {
    private List<OrderItemCreateRequestDTO> orderItemList;
}
