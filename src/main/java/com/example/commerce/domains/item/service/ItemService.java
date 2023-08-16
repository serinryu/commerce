package com.example.commerce.domains.item.service;

import com.example.commerce.domains.item.domain.ItemEntity;
import com.example.commerce.domains.item.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemResponseDTO findItem(Long id){
        ItemEntity itemEntity = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        return new ItemResponseDTO(itemEntity);
    }

    @Transactional
    public Long saveItem(AddItemRequestDTO request) {
        ItemEntity newItem = ItemEntity.builder()
                .name(request.getName())
                .imagePath(request.getImagePath())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .categoryId(request.getCategoryId())
                .build();
        ItemEntity savedItem = itemRepository.save(newItem);

        return savedItem.getId();
    }
}
