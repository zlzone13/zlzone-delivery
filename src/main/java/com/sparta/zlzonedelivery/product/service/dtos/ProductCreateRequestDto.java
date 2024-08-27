package com.sparta.zlzonedelivery.product.service.dtos;

import java.util.UUID;

public record ProductCreateRequestDto(
        UUID storeId,
        String name,
        String description,
        Integer price
) {

}
