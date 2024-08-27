package com.sparta.zlzonedelivery.product.service.dtos;

import java.util.UUID;

public record ProductUpdateRequestDto(
        UUID productId,
        String name,
        String description,
        Integer price,
        Boolean isShown
) {

}
