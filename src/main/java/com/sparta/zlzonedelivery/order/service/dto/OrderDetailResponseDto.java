package com.sparta.zlzonedelivery.order.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.zlzonedelivery.order.entity.Order;
import com.sparta.zlzonedelivery.order.entity.OrderStatus;
import com.sparta.zlzonedelivery.order.entity.OrderType;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
        Instant createdAt,
        String createdBy,
        Instant updatedAt,
        String updatedBy,
        Instant deletedAt,
        String deletedBy,
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
                .createdAt(Instant.from(order.getCreatedAt()))
                .createdBy(order.getCreatedBy())
                .updatedAt(Instant.from(order.getUpdatedAt()))
                .updatedBy(order.getUpdatedBy())
                .deletedAt(Instant.from(order.getDeletedAt()))
                .deletedBy(order.getDeletedBy())
                .products(order.getOrderProducts().stream()
                        .map(op -> new OrderItemDto(op.getProduct().getId(), op.getQuantity()))
                        .toList())
                .build();
    }

}
