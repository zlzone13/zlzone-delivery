package com.sparta.zlzonedelivery.global.auth.controller.dto;

import com.sparta.zlzonedelivery.user.User;
import com.sparta.zlzonedelivery.user.UserRole;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserSignUpRequestDto(
        @Size(min = 4, max = 10)
        @Pattern(regexp = "^[a-z0-9]+$")
        String username,
        @Size(min = 8, max = 15)
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>~`\\\\\\[\\]/\\-+=_]+$")
        String password,
        String nickname,
        UserRole role,
        @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?") // RFC 2822
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
