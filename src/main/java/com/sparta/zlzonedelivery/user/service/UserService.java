package com.sparta.zlzonedelivery.user.service;

import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.user.User;
import com.sparta.zlzonedelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATED_USERNAME);
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.encodePassword(encodedPassword);
        userRepository.save(user);
    }

    public User getUser(Long userId, User curUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (curUser.isAdmin()) return user;

        if (!userId.equals(curUser.getId())) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        return user;
    }

    public void updateUser(Long userId, User user) {
        User oldUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (user.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.encodePassword(encodedPassword);
        }

        oldUser.updateInfo(user);
        userRepository.save(oldUser);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        userRepository.deleteById(userId);
    }

    public Page<User> searchUsers(String userName, Pageable pageable) {
        if (userName == null) {
            return userRepository.findAll(pageable);
        }
        return userRepository.findAllByUsernameContaining(userName, pageable);
    }

    public boolean verifyUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) return false;
        User user = optionalUser.get();
        if (!user.getUsername().equals(username)) return false;
        return passwordEncoder.matches(password, user.getPassword());
    }

}
