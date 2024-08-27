package com.sparta.zlzonedelivery.product.service.dtos;

import java.util.UUID;

public record ProductCreateRequestDto(
        String name,
        String description,
        Integer price
) {

}
