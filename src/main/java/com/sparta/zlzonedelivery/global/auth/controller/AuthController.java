package com.sparta.zlzonedelivery.global.auth.controller;

import com.sparta.zlzonedelivery.global.auth.controller.dto.UserLoginRequestDto;
import com.sparta.zlzonedelivery.global.auth.controller.dto.UserLoginResponseDto;
import com.sparta.zlzonedelivery.global.auth.controller.dto.UserSignUpRequestDto;
import com.sparta.zlzonedelivery.global.auth.jwt.JwtUtil;
import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.user.User;
import com.sparta.zlzonedelivery.user.UserRole;
import com.sparta.zlzonedelivery.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    private final UserService userService;

    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid UserSignUpRequestDto requestDto) {

        User user = requestDto.toEntity();

        userService.createUser(user);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        if (!userService.verifyUser(requestDto.username(), requestDto.password())) {
            throw new CustomException(ErrorCode.USERNAME_NOT_FOUND);
        }

        String token = jwtUtil.createToken(requestDto.username(), UserRole.CUSTOMER);

        return new UserLoginResponseDto(token);
    }

}
