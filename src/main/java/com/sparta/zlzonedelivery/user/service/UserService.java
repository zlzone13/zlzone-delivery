package com.sparta.zlzonedelivery.user.service;

import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.user.User;
import com.sparta.zlzonedelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        if (userRepository.existsByNickname(user.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User encodedUser = user.encodePassword(encodedPassword);
        userRepository.save(encodedUser);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void updateUser(User user) {

    }

    public void deleteUser(Long userId) {

    }

    public Page<User> searchUsers(Long userId, String userName, Pageable pageable) {
        return null;
    }

    public boolean verifyUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) return false;
        User user = optionalUser.get();
        if (!user.getUsername().equals(username)) return false;
        return passwordEncoder.matches(password, user.getPassword());
    }

}
