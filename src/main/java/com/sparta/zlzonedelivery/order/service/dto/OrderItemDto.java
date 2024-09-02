package com.sparta.zlzonedelivery.order.service.dto;

import java.util.UUID;

public record OrderItemDto(

        UUID productId,
        Integer quantity

) {

}
