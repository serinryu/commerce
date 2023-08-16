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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    public void testFindItem() {
        // given
        ItemEntity mockItemEntity = ItemEntity.builder()
            .name("Test Item")
            .imagePath("example")
            .price(1000)
            .stockQuantity(2)
            .categoryId(3L)
                    .build();
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
}
