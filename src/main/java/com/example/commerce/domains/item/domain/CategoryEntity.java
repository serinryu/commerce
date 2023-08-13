package com.example.commerce.domains.item.domain;

import com.example.commerce.common.config.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String categoryName;

    @Builder
    public CategoryEntity(String categoryName) {
        this.categoryName = categoryName;
    }
}