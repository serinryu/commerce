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
        assertEquals("Test Item", itemResponseDTO.getName());
        assertEquals(1000, itemResponseDTO.getPrice());
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
        assertEquals("Sample Item", savedItemResponseDTO.getName());
        assertEquals("sample.jpg", savedItemResponseDTO.getImagePath());
        assertEquals(1000, savedItemResponseDTO.getPrice());
        assertEquals(10, savedItemResponseDTO.getStockQuantity());
    }

    private ItemCreateRequestDTO createItemCreateRequestDTO(){
        return ItemCreateRequestDTO.builder()
                .name("Sample Item")
                .imagePath("sample.jpg")
                .price(1000)
                .stockQuantity(10)
                .categoryId(3L)
                .build();
    }

    private ItemEntity createItemEntity(){
        return ItemEntity.builder()
                .name("Test Item")
                .imagePath("example")
                .price(1000)
                .stockQuantity(2)
                .categoryId(3L)
                .build();
    }

}
