package com.example.commerce.domains.order.domain;

import com.example.commerce.common.config.BaseEntity;
import com.example.commerce.domains.delivery.domain.DeliveryEntity;
import com.example.commerce.domains.member.domain.MemberEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    private int totalAmount;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    private boolean removed;

    private LocalDateTime removedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity orderer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private DeliveryEntity deliveryInformation;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity> orderItemList = new ArrayList<>();

    @Builder
    public OrderEntity(MemberEntity orderer, DeliveryEntity deliveryInformation, List<OrderItemEntity> orderItemEntityList) {
        this.orderer = orderer;
        this.deliveryInformation = deliveryInformation;
        this.setOrderItemList(orderItemEntityList);
        this.status = OrderStatus.ORDERED_STATUS;
    }

    // Entity 에서 setter 사용 지양하라고 했는데?
    // setter를 사용할때에 도메인 개념을 해하기 때문에 set보다는 조금 더 해당메소드가 하는 기능을 잘 설명할수 있는 이름으로 메소드를 작명해야 하나
    // 해당 메소드는 private 으로 외부에서 호출할 일이 없으므로 괜찮음
    private void setOrderItemList(List<OrderItemEntity> orderItemEntityList) {
        orderItemEntityList.stream()
                .forEach(orderItemEntity -> this.orderItemList.add(orderItemEntity));
        this.calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        this.totalAmount = this.orderItemList.stream()
                .mapToInt(orderItem -> orderItem.getOrderItemAmount())
                .sum();
    }

}