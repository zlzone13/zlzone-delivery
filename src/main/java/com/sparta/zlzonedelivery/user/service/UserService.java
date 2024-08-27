package com.sparta.zlzonedelivery.user.service;

import com.sparta.zlzonedelivery.user.User;
import com.sparta.zlzonedelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow();
    }

    public void updateUser(User user) {

    }

    public void deleteUser(Long userId) {

    }

    public Page<User> searchUsers(Long userId, String userName, Pageable pageable) {
        return null;
    }

}
