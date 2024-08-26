package com.sparta.zlzonedelivery.user;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;

@Table(name = "p_users")
@Entity
@SQLDelete(sql = "UPDATE p_users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String nickname;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
