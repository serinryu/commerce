package com.example.commerce.domains.order.service;


import com.example.commerce.domains.order.domain.OrderEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class OrderResponseDTO {
    private Long id;
    private String status;

    public static OrderResponseDTO fromEntity(OrderEntity orderEntity){
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(orderEntity.getId());
        dto.setStatus(String.valueOf(orderEntity.getStatus()));

        return dto;
    }
}