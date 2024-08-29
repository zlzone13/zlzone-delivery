package com.sparta.zlzonedelivery.category.service.dto;

import com.sparta.zlzonedelivery.category.entity.Category;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
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

    public static CategorySingleResponseDto fromCategory(Category category) {
        return CategorySingleResponseDto.builder()
                .categoryId(category.getId())
                .categoryName(category.getCategoryName())
                .createdAt(category.getCreatedAt())
                .createdBy(category.getCreatedBy())
                .updatedAt(category.getUpdatedAt())
                .updatedBy(category.getUpdatedBy())
                .deletedAt(category.getDeletedAt())
                .deletedBy(category.getDeletedBy())
                .build();
    }

}
