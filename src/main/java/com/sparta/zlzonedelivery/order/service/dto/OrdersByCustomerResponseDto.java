package com.sparta.zlzonedelivery.order.service.dto;

import com.sparta.zlzonedelivery.order.entity.Order;
import com.sparta.zlzonedelivery.order.entity.OrderStatus;
import com.sparta.zlzonedelivery.order.entity.OrderType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrdersByCustomerResponseDto(

        UUID orderId,
//        UUID addressId,
        UUID storeId,
        OrderType orderType,
        Integer totalAmount,
        OrderStatus orderStatus

) {

    public static OrdersByCustomerResponseDto fromEntity(Order order) {
        return OrdersByCustomerResponseDto.builder()
                .orderId(order.getId())
//                .addressId(address.getId())
                .storeId(order.getStore().getId())
                .orderType(order.getOrderType())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .build();
    }

}
