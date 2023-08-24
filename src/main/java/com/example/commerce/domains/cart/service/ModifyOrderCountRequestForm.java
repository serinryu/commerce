package com.example.commerce.domains.cart.service;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyOrderCountRequestForm {
    private Long itemId;
    @Min(1)
    private Integer orderCount;
}