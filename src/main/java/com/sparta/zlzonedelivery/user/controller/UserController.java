package com.sparta.zlzonedelivery.user.controller;

import com.sparta.zlzonedelivery.global.auth.jwt.JwtUtil;
import com.sparta.zlzonedelivery.global.auth.security.UserDetailsImpl;
import com.sparta.zlzonedelivery.user.User;
import com.sparta.zlzonedelivery.user.controller.dto.UserGetResponseDto;
import com.sparta.zlzonedelivery.user.controller.dto.UserSearchResponseDto;
import com.sparta.zlzonedelivery.user.controller.dto.UserUpdateRequestDto;
import com.sparta.zlzonedelivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @GetMapping("/{userId}")
    public UserGetResponseDto getUser(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User curUser = userDetails.getUser();
        User user = userService.getUser(userId, curUser);
        return new UserGetResponseDto(user);
    }

    @PatchMapping("/{userId}")
    public void updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequestDto requestDto) {
        User user = requestDto.toEntity();
        userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping
    public List<UserSearchResponseDto> searchUsers(@RequestParam(value = "username", required = false) String username,
                                                   Pageable pageable) {

        Page<User> users = userService.searchUsers(username, pageable);
        log.info("page size: {}", users.getSize());
        return users.map(UserSearchResponseDto::new)
                .toList();
    }

}
