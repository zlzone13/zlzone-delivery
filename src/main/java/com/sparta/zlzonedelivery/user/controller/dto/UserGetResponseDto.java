package com.sparta.zlzonedelivery.user.controller.dto;

import com.sparta.zlzonedelivery.user.User;
import com.sparta.zlzonedelivery.user.UserRole;

public record UserGetResponseDto(
        String username,
        String nickname,
        String email,
        UserRole role
) {

    public UserGetResponseDto(User user) {
        this(
                user.getUsername(),
                user.getNickname(),
                user.getEmail(),
                user.getRole()
        );
    }

}
