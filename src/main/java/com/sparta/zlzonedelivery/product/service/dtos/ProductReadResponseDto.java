package com.sparta.zlzonedelivery.product.service.dtos;

import com.sparta.zlzonedelivery.product.entity.Product;
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

    public static ProductReadResponseDto fromEntity(Product product) {
        return ProductReadResponseDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .productCategory(product.getProductCategory().getProductCategoryName())
                .build();
    }

}
