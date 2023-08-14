package com.example.commerce.domains.item.domain;

import com.example.commerce.common.config.BaseEntity;
import com.example.commerce.domains.item.service.NotEnoughStockQuantityException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemEntity extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String imagePath;
    private String name;
    private int price;
    private int stockQuantity;

    private Long categoryId;

    /* 직접 참조
    @OneToMany
    @JoinColumn(name = "category_id")
    private List<CategoryEntity> categoryList;
     */

    @Builder
    public ItemEntity(String name, String imagePath, int price, int stockQuantity, Long categoryId) {
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
    }

    // ==== 비즈니스 로직 ====
    public void removeStockQuantity(int orderQuantity) {
        int restStockQuantity = this.stockQuantity - orderQuantity;
        if(restStockQuantity < 0)
            throw new NotEnoughStockQuantityException();
        this.stockQuantity = restStockQuantity;
    }

    public void addStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

}