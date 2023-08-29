package com.example.commerce.domains.order.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class MyOrderSummaryDTO {
    private List<OrderResponseDTO> myOrderList;
    private int total;
}
