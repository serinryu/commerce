package com.example.commerce.domains.order.presentation;

import com.example.commerce.common.exception.SuccessCode;
import com.example.commerce.common.exception.response.SuccessResponse;
import com.example.commerce.domains.member.service.MemberResponseDTO;
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
    public ResponseEntity<SuccessResponse<OrderResponseDTO>> createOrder(
            @RequestParam Long ordererId,
            @RequestBody OrderCreateRequestDTO orderCreateRequestDTO) {
        OrderResponseDTO data = orderService.order(ordererId, orderCreateRequestDTO);
        return ResponseEntity
                .status(SuccessCode.CREATE_SUCCESS.getStatus())
                .body(SuccessResponse.of(SuccessCode.CREATE_SUCCESS, data));
    }

    // 주문 취소
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<SuccessResponse<Long>> cancelOrder(@PathVariable Long orderId) {
        orderService.cancel(orderId);
        return ResponseEntity
                .status(SuccessCode.DELETE_SUCCESS.getStatus())
                .body(SuccessResponse.of(SuccessCode.DELETE_SUCCESS, orderId));
    }
}
