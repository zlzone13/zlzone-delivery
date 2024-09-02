package com.sparta.zlzonedelivery.order.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.zlzonedelivery.order.entity.Order;
import com.sparta.zlzonedelivery.order.entity.OrderStatus;
import com.sparta.zlzonedelivery.order.entity.OrderType;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record OrderDetailResponseDto(

        UUID orderId,
        Long userId,
//        UUID addressId,
        UUID storeId,
        OrderType orderType,
        String toRiderRequest,
        String toOwnerRequest,
        Integer totalAmount,
        OrderStatus orderStatus,
        List<OrderItemDto> products

) {

    public static OrderDetailResponseDto fromEntity(Order order) {
        return OrderDetailResponseDto.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
//                .addressId(order.getAddress().getId())
                .storeId(order.getStore().getId())
                .orderType(order.getOrderType())
                .toRiderRequest(order.getToRiderRequest())
                .toOwnerRequest(order.getToOwnerRequest())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .products(order.getOrderProducts().stream()
                        .map(op -> new OrderItemDto(op.getProduct().getId(), op.getQuantity()))
                        .toList())
                .build();
    }

}
