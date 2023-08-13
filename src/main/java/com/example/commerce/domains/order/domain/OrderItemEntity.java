package com.example.commerce.domains.order.domain;

import com.example.commerce.common.config.BaseEntity;
import com.example.commerce.domains.item.domain.ItemEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemEntity extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private int orderQuantity;
    private int orderItemAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Builder
    public OrderItemEntity(ItemEntity item, int orderQuantity) {
        this.order(item, orderQuantity);
        this.orderQuantity = orderQuantity;
        this.calculateOrderItemTotalAmount();
    }

    private void order(ItemEntity item, int orderQuantity) {
        //item.removeStockQuantity(orderQuantity);
        this.item = item;
    }

    private void calculateOrderItemTotalAmount() {
        this.orderItemAmount = this.item.getPrice() * orderQuantity;
    }
}