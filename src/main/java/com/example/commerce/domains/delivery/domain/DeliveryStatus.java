package com.example.commerce.domains.delivery.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public enum DeliveryStatus {
    READY_STATUS, SHIPPING_STATUS, COMPLETE_STATUS
}