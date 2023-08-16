package com.example.commerce.domains.order.service;

import com.example.commerce.common.value.Address;
import com.example.commerce.domains.item.domain.ItemEntity;
import com.example.commerce.domains.item.domain.ItemRepository;
import com.example.commerce.domains.member.domain.MemberEntity;
import com.example.commerce.domains.member.service.MemberService;
import com.example.commerce.domains.order.domain.OrderEntity;
import com.example.commerce.domains.order.domain.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/*
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private ItemRepository itemRepository;

    @Test
    public void testOrderCreation() {
        // Mock data
        Long ordererId = 1L;
        MemberEntity orderer = MemberEntity.builder()
                .authId("testuser")
                .authPw("password")
                .name("Test User")
                .phone("123-456-7890")
                .address(new Address("Test City", "Test Street"))
                .build();
        when(memberService.findMember(ordererId)).thenReturn(orderer);

        // Mock itemRepository behavior
        Long itemId = 2L;
        ItemEntity item = ItemEntity.builder()
                .name("testitem")
                .imagePath(null)
                .price(1000)
                .stockQuantity(4)
                .categoryId(2L)
                .build();
        when(itemRepository.findById(itemId)).thenReturn(java.util.Optional.of(item));

        // when

        // Create OrderItemRequestDTO
        OrderItemRequestDTO orderItemRequestDTO = new OrderItemRequestDTO();
        orderItemRequestDTO.setItemId(itemId);
        orderItemRequestDTO.setOrderCount(2); // Set the order count

        // Create OrderRequestDTO
        List<OrderItemRequestDTO> orderItemList = new ArrayList<>();
        orderItemList.add(orderItemRequestDTO);

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setOrderItemList(orderItemList);

        // Call the order method
        Long orderId = orderService.order(ordererId, orderRequestDTO);

        // then
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(item, times(1)).removeStockQuantity(1);
    }

    @Test
    public void testOrderCancellation() {
        // Mock data
        Long orderId = 1L;
        OrderEntity order = OrderEntity.builder()
                .build();

        // Mock orderRepository behavior
        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(order));

        // Call the cancel method
        orderService.cancel(orderId);

        // Verify that order.cancel() was called once
        verify(order, times(1)).cancel();

        // You can add more assertions here based on your business logic
    }
}
*/