package com.sparta.zlzonedelivery.order.service.dto;

import com.sparta.zlzonedelivery.order.entity.OrderType;

import java.util.List;
import java.util.UUID;

public record OrderCreateOnlineRequestDto(

//        UUID addressId,
//        UUID paymentId,
        String toRiderRequest,
        String toOwnerRequest,
        List<OrderItemDto> items
//        Integer totalAmount

        ) {

}
