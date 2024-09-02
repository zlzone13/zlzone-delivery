package com.sparta.zlzonedelivery.order.service.dto;

import com.sparta.zlzonedelivery.order.entity.Order;
import com.sparta.zlzonedelivery.order.entity.OrderStatus;
import com.sparta.zlzonedelivery.order.entity.OrderType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrdersByOwnerResponseDto(

        UUID orderId,
//        UUID addressId,
        Long userId,
        OrderType orderType,
        Integer totalAmount,
        OrderStatus orderStatus

) {

    public static OrdersByOwnerResponseDto fromEntity(Order order) {
        return OrdersByOwnerResponseDto.builder()
                .orderId(order.getId())
//                .addressId(address.getId())
                .userId(order.getUser().getId())
                .orderType(order.getOrderType())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .build();
    }

}
