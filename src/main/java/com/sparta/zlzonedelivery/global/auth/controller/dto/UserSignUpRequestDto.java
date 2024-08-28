package com.sparta.zlzonedelivery.global.auth.controller.dto;

import com.sparta.zlzonedelivery.user.User;
import com.sparta.zlzonedelivery.user.UserRole;

public record UserSignUpRequestDto(
        String username,
        String password,
        String nickname,
        UserRole role,
        String email
) {

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .role(role)
                .email(email)
                .build();
    }

}
