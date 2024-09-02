package com.sparta.zlzonedelivery.order.service.dto;

import java.util.List;

public record OrderCreateOfflineRequestDto(

        //        UUID paymentId,
        String toOwnerRequest,
        List<OrderItemDto> items

) {

}
