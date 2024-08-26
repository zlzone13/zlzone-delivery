package com.sparta.zlzonedelivery.user.repository;

import com.sparta.zlzonedelivery.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
