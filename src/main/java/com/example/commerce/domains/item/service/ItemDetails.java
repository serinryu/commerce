package com.example.commerce.domains.item.service;

import com.example.commerce.domains.item.domain.ItemEntity;
import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
public class ItemDetails {
    private Long id;
    private String imagePath;
    private String name;
    private int price;
    private int stockQuantity;

    public ItemDetails(ItemEntity itemEntity) {
        this.id = itemEntity.getId();
        this.imagePath = itemEntity.getImagePath();
        this.name = itemEntity.getName();
        this.price = itemEntity.getPrice();
        this.stockQuantity = itemEntity.getStockQuantity();
    }
}