package com.sparta.zlzonedelivery.product.service.dtos;

public record ProductCreateRequestDto(
        String name,
        String description,
        Integer price,
        String productCategory
) {

}
