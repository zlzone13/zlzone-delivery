package com.sparta.zlzonedelivery.user.controller;

import com.sparta.zlzonedelivery.user.controller.dto.GetUserResponseDto;
import com.sparta.zlzonedelivery.user.controller.dto.findUserReponseDto;
import com.sparta.zlzonedelivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public GetUserResponseDto getUser(@PathVariable Long userId) {
        return null;
    }

    @PatchMapping("/{userId}")
    public void updateUser(@PathVariable Long userId) {

    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {

    }

    @GetMapping
    public Page<findUserReponseDto> searchUsers(@RequestParam("user-id") Long userId,
                                                @RequestParam("nickname") String nickname,
                                                Pageable pageable) {
        return null;
    }

}
