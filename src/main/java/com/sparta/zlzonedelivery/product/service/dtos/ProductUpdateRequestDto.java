package com.sparta.zlzonedelivery.product.service.dtos;

public record ProductUpdateRequestDto(
        String name,
        String description,
        Integer price,
        Boolean isShown
) {

}
