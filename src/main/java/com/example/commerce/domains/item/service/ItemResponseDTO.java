package com.example.commerce.domains.item.service;

import com.example.commerce.domains.item.domain.ItemEntity;
import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
public class ItemResponseDTO {
    private Long id;
    private String imagePath;
    private String name;
    private int price;
    private int stockQuantity;
    // no categoryId

    public ItemResponseDTO fromEntity(ItemEntity itemEntity) {
        ItemResponseDTO dto = new ItemResponseDTO();
        dto.setId(itemEntity.getId());
        dto.setImagePath(itemEntity.getImagePath());
        dto.setName(itemEntity.getName());
        dto.setPrice(itemEntity.getPrice());
        dto.setStockQuantity(itemEntity.getStockQuantity());

        return dto;
    }

}