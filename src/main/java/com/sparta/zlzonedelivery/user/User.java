package com.sparta.zlzonedelivery.user;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Table(name = "p_users")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE p_users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Column(length = 10)
    private String username;

    @Getter
    @Column(length = 10)
    private String nickname;

    @Getter
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    // RFC 2822
    @Column(unique = true)
    private String email;

    @Getter
    private String password;

    @Getter
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String username, String nickname, String email, String password, UserRole role) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User encodePassword(String encodedPassword) {
        return User.builder()
                .username(this.username)
                .nickname(this.nickname)
                .password(encodedPassword)
                .email(this.email)
                .role(this.role)
                .build();
    }

}
