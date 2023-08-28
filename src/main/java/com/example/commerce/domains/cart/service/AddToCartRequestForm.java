package com.example.commerce.domains.cart.service;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddToCartRequestForm {
    private Long itemId;
    @Min(1)
    private Integer orderCount;
}