package com.sparta.zlzonedelivery.user.controller.dto;

import com.sparta.zlzonedelivery.user.User;
import com.sparta.zlzonedelivery.user.UserRole;

public record UserUpdateRequestDto(
        String nickname,
        String email,
        String password,
        UserRole role
) {

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

}
