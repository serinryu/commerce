package com.example.commerce.domains.order.service;

import com.example.commerce.common.value.Address;
import com.example.commerce.domains.delivery.domain.DeliveryEntity;
import com.example.commerce.domains.item.domain.ItemEntity;
import com.example.commerce.domains.item.domain.ItemRepository;
import com.example.commerce.domains.item.exception.NotEnoughStockQuantityException;
import com.example.commerce.domains.member.domain.MemberEntity;
import com.example.commerce.domains.member.domain.MemberRepository;
import com.example.commerce.domains.order.domain.OrderEntity;
import com.example.commerce.domains.order.domain.OrderItemEntity;
import com.example.commerce.domains.order.domain.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ItemRepository itemRepository;

    @Test
    public void testOrderItem() {
        // given

        // 주문자
        Long TEST_MEMBER_ID = 1L;
        MemberEntity TEST_MEMBER = createMember();
        when(memberRepository.findById(TEST_MEMBER_ID)).thenReturn(Optional.of(TEST_MEMBER));

        // 주문 상품
        Long TEST_ITEM_ID = 2L;
        int stockQuantity = 10;
        ItemEntity TEST_ITEM = createItem(stockQuantity);
        ReflectionTestUtils.setField(TEST_ITEM, "id", TEST_ITEM_ID);
        when(itemRepository.findById(TEST_ITEM_ID)).thenReturn(Optional.of(TEST_ITEM));

        // 장바구니 비우기

        // 주문 요청
        int orderCount = 2;
        OrderCreateRequestDTO orderCreateRequestDTO = createOrderRequestDTO(TEST_ITEM_ID, orderCount);

        // 주문
        OrderEntity TEST_ORDER = createOrder(TEST_MEMBER, TEST_ITEM, orderCount);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(TEST_ORDER);

        // when
        OrderResponseDTO order = orderService.order(TEST_MEMBER_ID, orderCreateRequestDTO);

        // then
        assertNotNull(order);
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    public void testOrderCancellation() {
        // given
        Long TEST_ORDER_ID = 1l;
        OrderEntity TEST_ORDER = mock(OrderEntity.class);
        doNothing().when(TEST_ORDER).cancel();
        when(orderRepository.findById(any())).thenReturn(Optional.of(TEST_ORDER));

        // when
        orderService.cancel(TEST_ORDER_ID);

        // then
        verify(TEST_ORDER, times(1)).cancel();
    }

    @Test
    public void testNotEnoghStockQuantity() throws Exception {
        //given
        Long TEST_MEMBER_ID = 1l;
        MemberEntity TEST_MEMBER = createMember();
        ReflectionTestUtils.setField(TEST_MEMBER, "id", TEST_MEMBER_ID);
        when(memberRepository.findById(TEST_MEMBER_ID)).thenReturn(Optional.of(TEST_MEMBER));

        Long TEST_ITEM_ID = 2l;
        int stockQuantity = 1;
        ItemEntity TEST_ITEM = createItem(stockQuantity);
        ReflectionTestUtils.setField(TEST_ITEM, "id", TEST_ITEM_ID);
        when(itemRepository.findById(TEST_ITEM_ID)).thenReturn(Optional.of(TEST_ITEM));

        //when
        int orderCount = 2;

        //then
        assertThrows(NotEnoughStockQuantityException.class,
                () -> orderService.order(TEST_MEMBER.getId(), createOrderRequestDTO(TEST_ITEM_ID, orderCount)));
    }

    private ItemEntity createItem(int stockQuantity) {
        ItemEntity TEST_ITEM = ItemEntity.builder()
                .name("TEST")
                .price(1000)
                .stockQuantity(stockQuantity)
                .imagePath("TEST")
                .categoryId(1l)
                .build();
        return TEST_ITEM;
    }

    private MemberEntity createMember() {
        MemberEntity TEST_MEMBER = MemberEntity.builder()
                .authId("TEST")
                .authPw("TEST")
                .address(new Address("TEST", "TEST"))
                .build();

        return TEST_MEMBER;
    }

    private OrderEntity createOrder(MemberEntity TEST_MEMBER, ItemEntity TEST_ITEM, int orderCount) {
        OrderEntity TEST_ORDER = OrderEntity.builder()
                .deliveryInformation(new DeliveryEntity(TEST_MEMBER.getAddress()))
                .orderer(TEST_MEMBER)
                .orderItemEntityList(List.of(
                        OrderItemEntity.builder()
                                .orderCount(orderCount)
                                .item(TEST_ITEM)
                                .build()
                ))
                .build();
        Long TEST_ORDER_ID = 1L;
        ReflectionTestUtils.setField(TEST_ORDER, "id", TEST_ORDER_ID);

        return TEST_ORDER;
    }

    private OrderCreateRequestDTO createOrderRequestDTO(Long itemId, int orderCount) {
        List<OrderItemCreateRequestDTO> orderLineRequests = List.of(new OrderItemCreateRequestDTO(itemId, orderCount));

        OrderCreateRequestDTO orderRequest = new OrderCreateRequestDTO(orderLineRequests);

        return orderRequest;
    }

}
