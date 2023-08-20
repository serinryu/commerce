package com.example.commerce.domains.item.presentation;

import com.example.commerce.domains.item.service.ItemCreateRequestDTO;
import com.example.commerce.domains.item.service.ItemResponseDTO;
import com.example.commerce.domains.item.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @Test
    public void testFindItem() throws Exception {
        // Given
        ItemResponseDTO responseDTO = new ItemResponseDTO();

        Mockito.when(itemService.findItem(Mockito.anyLong()))
                .thenReturn(responseDTO);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/items/{id}", TEST_ITEM_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.data.name").value(responseDTO.getName()));
    }

    @Test
    public void testSaveItem() throws Exception {
        // Given
        ItemCreateRequestDTO requestDTO = createItemCreateRequestDTO();

        ItemResponseDTO responseDTO = createItemResponseDTO();
        // Set up your responseDTO with expected data

        Mockito.when(itemService.saveItem(Mockito.any(ItemCreateRequestDTO.class)))
                .thenReturn(responseDTO);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.data.name").value(responseDTO.getName()));
    }

    private Long TEST_ITEM_ID = 123L;
    private String imagePath = "sample.jpg";
    private String name = "Sample Item";
    private int price = 1000;
    private int stockQuantity = 10;

    private ItemResponseDTO createItemResponseDTO(){
        return ItemResponseDTO.builder()
                .id(TEST_ITEM_ID)
                .imagePath(imagePath)
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }

    private ItemCreateRequestDTO createItemCreateRequestDTO(){
        return ItemCreateRequestDTO.builder()
                .name(name)
                .imagePath(imagePath)
                .price(price)
                .stockQuantity(stockQuantity)
                .categoryId(3L)
                .build();
    }
}
