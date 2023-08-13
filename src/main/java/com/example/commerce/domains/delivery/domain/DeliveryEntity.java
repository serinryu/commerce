package com.example.commerce.domains.delivery.domain;

import com.example.commerce.common.config.BaseEntity;
import com.example.commerce.common.value.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryEntity extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus status;

    @Builder
    public DeliveryEntity(Address address) {
        this.address = address;
        this.status = DeliveryStatus.READY_STATUS;
    }
}