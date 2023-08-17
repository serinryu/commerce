package com.example.commerce.domains.item.service;

import com.example.commerce.domains.item.domain.ItemEntity;
import com.example.commerce.domains.item.domain.ItemRepository;
import com.example.commerce.domains.item.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    // 상품 조회
    public ItemResponseDTO findItem(Long id){
        ItemEntity itemEntity = itemRepository.findById(id)
                .orElseThrow(() -> ItemNotFoundException.EXCEPTION);
        ItemResponseDTO item = new ItemResponseDTO();
        return item.fromEntity(itemEntity);
    }

    // 상품 저장
    @Transactional
    public ItemResponseDTO saveItem(ItemCreateRequestDTO itemCreateRequestDTO) {
        ItemEntity itemEntity = itemRepository.save(itemCreateRequestDTO.toEntity());
        ItemResponseDTO item = new ItemResponseDTO();
        return item.fromEntity(itemEntity);
    }
}
