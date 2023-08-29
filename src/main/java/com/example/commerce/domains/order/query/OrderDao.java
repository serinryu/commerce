package com.example.commerce.domains.order.query;

import com.example.commerce.domains.order.domain.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderDao {
    Page<OrderEntity> getMyOrders(Long ordererId, Pageable pageable);
    Optional<OrderEntity> getMyOrderDetails(Long orderId);
}