package com.sparta.zlzonedelivery.product.service.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductReadResponseDto(
        UUID productId,
        String name,
        String description,
        Integer price,
        String productCategory
) {

}
