package com.sparta.zlzonedelivery.user.repository;

import com.sparta.zlzonedelivery.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

}
