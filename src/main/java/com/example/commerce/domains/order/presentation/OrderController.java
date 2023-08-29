package com.example.commerce.domains.order.presentation;

import com.example.commerce.common.exception.SuccessCode;
import com.example.commerce.common.exception.response.SuccessResponse;
import com.example.commerce.domains.member.service.MemberResponseDTO;
import com.example.commerce.domains.order.service.MyOrderSummaryDTO;
import com.example.commerce.domains.order.service.OrderCreateRequestDTO;
import com.example.commerce.domains.order.service.OrderResponseDTO;
import com.example.commerce.domains.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문 목록 페이지
    // 삭제 되지 않은 주문내역만을 가져오고, 페이지네이션된 데이터를 가져옵니다.
    // 따라서, 복잡한 쿼리문 생성을 위해 QueryDsl 을 사용해보자.
    @GetMapping("/orders")
    public ResponseEntity<SuccessResponse<MyOrderSummaryDTO>> getOrderList(@RequestParam Long ordererId, Pageable pageable){
        MyOrderSummaryDTO data = orderService.getMyOrderSummary(ordererId, pageable);
        return ResponseEntity
                .status(SuccessCode.GET_INFO_SUCCESS.getStatus())
                .body(SuccessResponse.of(SuccessCode.GET_INFO_SUCCESS, data));
    }


    // 주문 하기
    // 이후에는 SpringSecurity의 기능을 이용해, 현재 로그인 중인 사용자의 정보를 얻어와 사용 (Authentication 객체)
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
