package com.sparta.zlzonedelivery.category.service.dto;

import java.util.UUID;

public record CategoryListResponseDto(
        UUID categoryId,
        String categoryName) {

}
