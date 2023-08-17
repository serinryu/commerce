package com.example.commerce.domains.item.service;

import com.example.commerce.domains.item.domain.ItemEntity;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ItemCreateRequestDTO {
    @Length(min = 3)
    private String name;
    @Length(min = 3)
    private String imagePath;
    @Min(0)
    private int price;
    @Min(1)
    private int stockQuantity;
    private Long categoryId;

    public ItemEntity toEntity() {
        return ItemEntity.builder()
            .name(name)
            .imagePath(imagePath)
            .price(price)
            .stockQuantity(stockQuantity)
            .categoryId(categoryId)
            .build();
    }
}