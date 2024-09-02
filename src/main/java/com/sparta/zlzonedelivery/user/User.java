package com.sparta.zlzonedelivery.user;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;

@ToString
@Table(name = "p_users")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(UserSignupListener.class)
@SQLRestriction(value = "is_public = true")
@SQLDelete(sql = """
        UPDATE p_users SET is_public = false,
                           deleted_at = CURRENT_TIMESTAMP
                       WHERE id = ?
        """)
public class User extends BaseEntity {

    @Id
    @Getter
    @GeneratedValue
    private Long id;

    @Getter
    @Column(length = 10)
    private String username;

    @Getter
    @Column(length = 10)
    private String nickname;

    @Getter
    @Column(unique = true)
    private String email;

    @Getter
    private String password;

    @Getter
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(Long id, String username, String nickname, String email, String password, UserRole role) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public boolean isAdmin() {
        return UserRole.MASTER == role || UserRole.MANAGER == role;
    }

    public void updateInfo(User user) {
        if (user.nickname != null) this.nickname = user.nickname;
        if (user.email != null) this.email = user.email;
        if (user.password != null) this.password = user.password;
        if (user.role != null) this.role = user.role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(nickname, user.nickname) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, nickname, email, password, role);
    }

}
