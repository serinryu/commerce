package com.example.commerce.domains.item.service;

import com.example.commerce.domains.item.domain.ItemEntity;
import com.example.commerce.domains.item.domain.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    // 상품 조회
    @Test
    public void testFindItem() {
        // given
        ItemEntity mockItemEntity = createItemEntity();
        Long itemId = mockItemEntity.getId();

        // when
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(mockItemEntity));

        // then
        ItemResponseDTO itemResponseDTO = itemService.findItem(itemId);

        assertNotNull(itemResponseDTO);
        assertEquals(itemId, itemResponseDTO.getId());
        assertEquals(name, itemResponseDTO.getName());
        assertEquals(price, itemResponseDTO.getPrice());
    }


    @Test
    public void testSaveItem() {
        // Mock data
        ItemCreateRequestDTO itemCreateRequestDTO = createItemCreateRequestDTO();
        ItemEntity itemEntity = itemCreateRequestDTO.toEntity();
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(itemEntity);

        // when
        ItemResponseDTO savedItemResponseDTO = itemService.saveItem(itemCreateRequestDTO);

        // then
        assertEquals(name, savedItemResponseDTO.getName());
        assertEquals(imagePath, savedItemResponseDTO.getImagePath());
        assertEquals(price, savedItemResponseDTO.getPrice());
        assertEquals(stockQuantity, savedItemResponseDTO.getStockQuantity());
    }

    private Long TEST_ITEM_ID = 123L;
    private String imagePath = "sample.jpg";
    private String name = "Sample Item";
    private int price = 1000;
    private int stockQuantity = 10;
    private Long categoryId = 3L;

    private ItemCreateRequestDTO createItemCreateRequestDTO(){
        return ItemCreateRequestDTO.builder()
                .name(name)
                .imagePath(imagePath)
                .price(price)
                .stockQuantity(stockQuantity)
                .categoryId(categoryId)
                .build();
    }

    private ItemEntity createItemEntity(){
        return ItemEntity.builder()
                .name(name)
                .imagePath(imagePath)
                .price(price)
                .stockQuantity(stockQuantity)
                .categoryId(categoryId)
                .build();
    }

}
