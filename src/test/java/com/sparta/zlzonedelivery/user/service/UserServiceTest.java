package com.sparta.zlzonedelivery.user.service;

import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.user.User;
import com.sparta.zlzonedelivery.user.UserRole;
import com.sparta.zlzonedelivery.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private static final User user = User.builder()
            .id(1L)
            .username("testuser")
            .email("test@example.com")
            .password("password")
            .nickname("testuser")
            .role(UserRole.CUSTOMER)
            .build();

    @Test
    @Order(1)
    @DisplayName("회원가입 성공")
    void createUser_Success() {
        // Arrange
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        userService.createUser(user);

        // Assert
        verify(userRepository).save(user);
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
    }

    @Test
    @Order(2)
    @DisplayName("회원가입 실패 - 중복된 이메일")
    void createUser_DuplicatedEmail() {
        // given
        User wrongUser = User.builder()
                .email(user.getEmail())
                .build();

        // when
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // then
        assertThrows(CustomException.class, () -> userService.createUser(wrongUser));
    }

    @Test
    @Order(3)
    @DisplayName("회원가입 실패 - 중복된 유저네임")
    void createUser_DuplicatedNickname() {
        // given
        User wrongUser = User.builder()
                .username(user.getUsername())
                .build();

        // when
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // then
        assertThrows(CustomException.class, () -> userService.createUser(wrongUser));
    }

    @Test
    @DisplayName("회원 조회 성공")
    void getUser_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUser(1L, user);

        // Assert
        assertThat(result).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    @DisplayName("회원 조회 실패 - 유저 없음")
    void getUser_NotFound() {
        // when
        Optional<User> optionalUser = userRepository.findById(2L);

        // then
        assertThat(optionalUser).isEmpty();
    }

    @Test
    @DisplayName("회원 정보 수정 성공")
    void updateUser_Success() {
        // Arrange
        User newUser = User.builder()
                .nickname("newNickname")
                .email("new@example.com")
                .password("newPassword")
                .role(UserRole.MANAGER)
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encodedNewPassword");

        // Act
        userService.updateUser(1L, newUser);

        // Assert
        assertThat(user.getNickname()).isEqualTo(newUser.getNickname());
        assertThat(user.getEmail()).isEqualTo(newUser.getEmail());
        assertThat(user.getPassword()).isEqualTo("encodedNewPassword");
    }

    @Test
    @DisplayName("회원 정보 수정 실패 - 유저 없음")
    void updateUser_NotFound() {
        // given, when
        Optional<User> optionalUser = userRepository.findById(2L);

        // then
        assertThat(optionalUser).isEmpty();
    }

    @Test
    @DisplayName("회원 삭제 성공")
    void deleteUser_Success() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("회원 검색 - 유저네임으로 검색")
    void searchUsers_WithUserName() {
        // given
        PageRequest pageable = PageRequest.of(0, 10);

        // when
        when(userRepository.findAllByUsernameContaining(anyString(), any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(user)));
        Page<User> users = userService.searchUsers("test", pageable);

        // then
        assertThat(users.getSize()).isGreaterThan(0);
    }

    @Test
    @DisplayName("회원 검색 - 유저네임 없이 검색")
    void searchUsers_WithoutUserName() {
        // Arrange
        PageRequest pageable = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(Collections.emptyList());
        when(userRepository.findAll(pageable)).thenReturn(page);

        // Act
        Page<User> result = userService.searchUsers(null, pageable);

        // Assert
        assertThat(result).isEqualTo(page);
    }

}
