package com.example.commerce.domains.order.presentation;

import com.example.commerce.domains.order.service.OrderCreateRequestDTO;
import com.example.commerce.domains.order.service.OrderResponseDTO;
import com.example.commerce.domains.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문하기
    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @RequestParam Long ordererId,
            @RequestBody OrderCreateRequestDTO orderCreateRequestDTO) {
        OrderResponseDTO response = orderService.order(ordererId, orderCreateRequestDTO);
        return ResponseEntity.ok(response);
    }

    // 주문 취소
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancel(orderId);
        return ResponseEntity.ok("Order canceled successfully");
    }
}
