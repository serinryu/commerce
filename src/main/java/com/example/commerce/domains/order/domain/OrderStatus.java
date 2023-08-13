package com.example.commerce.domains.order.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public enum OrderStatus {
    ORDERED_STATUS, SHIPPING_STATUS, CANCEL_STATUS
}
