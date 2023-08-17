package com.example.commerce.domains.order.presentation;

import com.example.commerce.domains.order.service.OrderCreateRequestDTO;
import com.example.commerce.domains.order.service.OrderItemCreateRequestDTO;
import com.example.commerce.domains.order.service.OrderResponseDTO;
import com.example.commerce.domains.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.example.commerce.domains.order.domain.OrderStatus.ORDERED_STATUS;
import static org.hamcrest.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    private OrderCreateRequestDTO createOrderRequestDTO(Long itemId, int orderCount) {
        List<OrderItemCreateRequestDTO> orderLineRequests = List.of(new OrderItemCreateRequestDTO(itemId, orderCount));

        OrderCreateRequestDTO orderRequest = new OrderCreateRequestDTO(orderLineRequests);

        return orderRequest;
    }

    private OrderResponseDTO createOrderResponseDTO(Long TEST_ORDER_ID, String status){
        return OrderResponseDTO.builder()
                .id(TEST_ORDER_ID)
                .status(status)
                .build();
    }

    @Test
    public void testCreateOrder() throws Exception {
        // Given
        Long TEST_ITEM_ID = 2L;
        int orderCount = 2;
        OrderCreateRequestDTO requestDTO = createOrderRequestDTO(TEST_ITEM_ID, orderCount);

        Long TEST_ORDER_ID = 123L;
        String status = ORDERED_STATUS.toString();
        OrderResponseDTO responseDTO = createOrderResponseDTO(TEST_ORDER_ID, status);

        Mockito.when(orderService.order(Mockito.anyLong(), Mockito.any(OrderCreateRequestDTO.class)))
                .thenReturn(responseDTO);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .param("ordererId", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.data.status").value(responseDTO.getStatus()));
    }

    @Test
    public void testCancelOrder() throws Exception {
        // Given
        Long orderId = 123L;

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{orderId}", orderId));

        // Then
        Mockito.verify(orderService).cancel(orderId);
    }
}
