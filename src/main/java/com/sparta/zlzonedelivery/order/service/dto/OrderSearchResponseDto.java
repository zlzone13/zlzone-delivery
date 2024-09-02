package com.sparta.zlzonedelivery.order.service.dto;

import com.sparta.zlzonedelivery.order.entity.Order;
import com.sparta.zlzonedelivery.order.entity.OrderStatus;
import com.sparta.zlzonedelivery.order.entity.OrderType;
import lombok.Builder;

@Builder
public record OrderSearchResponseDto(

        String storeName,
        OrderType orderType,
        OrderStatus orderStatus

) {

    public static OrderSearchResponseDto fromEntity(Order order) {
        return OrderSearchResponseDto.builder()
                .storeName(order.getStore().getStoreName())
                .orderType(order.getOrderType())
                .orderStatus(order.getOrderStatus())
                .build();
    }

}
