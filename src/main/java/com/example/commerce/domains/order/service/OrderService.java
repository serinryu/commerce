package com.example.commerce.domains.order.service;

import com.example.commerce.domains.delivery.domain.DeliveryEntity;
import com.example.commerce.domains.item.domain.ItemEntity;
import com.example.commerce.domains.item.domain.ItemRepository;
import com.example.commerce.domains.member.domain.MemberEntity;
import com.example.commerce.domains.member.domain.MemberRepository;
import com.example.commerce.domains.order.domain.OrderEntity;
import com.example.commerce.domains.order.domain.OrderItemEntity;
import com.example.commerce.domains.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문하기 (주문 생성 -> 주문 저장)
    public OrderResponseDTO order(Long ordererId, OrderCreateRequestDTO orderCreateRequestDTO){
        // 주문자 조회
        // MemberResponseDTO orderer = memberService.findMember(ordererId);
        MemberEntity orderer = memberRepository.findById(ordererId).get();

        // 배송지 생성
        DeliveryEntity deliveryInformation = DeliveryEntity.builder()
                .address(orderer.getAddress())
                .build();

        // 주문 상품 생성
        List<OrderItemEntity> orderItemEntityList = orderCreateRequestDTO.getOrderItemList()
                .stream()
                .map(ol -> {
                    // 각 주문 상품 (OrderItem)
                    ItemEntity item = itemRepository.findById(ol.getItemId()).get();
                    return new OrderItemEntity(item, ol.getOrderCount());
                })
                .collect(Collectors.toList());

        // 주문 상품 재고 줄이기
        orderItemEntityList.stream()
                .forEach(oi -> oi.removeStockQuantity());

        // 주문 생성
        OrderEntity orderEntity = OrderEntity.builder()
                .orderer(orderer)
                .deliveryInformation(deliveryInformation)
                .orderItemEntityList(orderItemEntityList)
                .build();

        /* 장바구니 비우기
        List<Long> itemIdList = orderCreateRequestDTO.getOrderItemList().stream()
                .map(ol -> ol.getItemId())
                .collect(Collectors.toList());
        // cart 생성 이후 로직
        */

        // 주문 저장
        OrderEntity order = orderRepository.save(orderEntity);
        return OrderResponseDTO.fromEntity(order);
    }

    // 주문 취소
    public void cancel(Long orderId){
        OrderEntity order = orderRepository.findById(orderId).get();
        order.cancel();
    }
}
