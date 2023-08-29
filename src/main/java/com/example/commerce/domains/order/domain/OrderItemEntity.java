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
    private Long orderItemId;

    private int orderCount; // 주문 상품의 갯수
    private int orderItemAmount; // 주문 상품의 가격

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Builder
    public OrderItemEntity(ItemEntity item, int orderCount) {
        this.order(item, orderCount);
        this.calculateOrderItemTotalAmount();
    }

    private void order(ItemEntity item, int orderCount) {
        item.removeStockQuantity(orderCount); // 주문된 상품의 갯수 만큼 재고 -1
        this.item = item;
        this.orderCount = orderCount;
    }

    private void calculateOrderItemTotalAmount() {
        this.orderItemAmount = this.item.getPrice() * orderCount;
    }

    // ==== 비즈니스 로직 ====
    public void cancel() {
        this.item.addStockQuantity(this.orderCount);
    }

    public void removeStockQuantity(){
        this.item.removeStockQuantity(orderCount);
    }
}