package com.sparta.zlzonedelivery.category.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategorySingleResponseDto(
        UUID categoryId,
        String categoryName,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {

}
