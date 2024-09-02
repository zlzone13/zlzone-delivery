package com.sparta.zlzonedelivery.user.controller.dto;

import com.sparta.zlzonedelivery.user.User;
import com.sparta.zlzonedelivery.user.UserRole;

import java.time.LocalDateTime;

public record UserSearchResponseDto(
        Long id,
        String username,
        String nickname,
        String email,
        UserRole role,
        Boolean isPublic,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {

    public UserSearchResponseDto(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getEmail(),
                user.getRole(),
                user.getIsPublic(),
                user.getCreatedAt(),
                user.getCreatedBy(),
                user.getUpdatedAt(),
                user.getUpdatedBy(),
                user.getDeletedAt(),
                user.getDeletedBy()
        );
    }

}
