package com.sparta.zlzonedelivery.address;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import com.sparta.zlzonedelivery.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "p_addresses")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SQLDelete(sql = """
        UPDATE p_addresses
        SET
            deleted_at = CURRENT_TIMESTAMP,
            is_public = false
        WHERE id = ?
        """)
@SQLRestriction(value = "is_public = true")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 5)
    @Getter
    private String zipcode;

    @Getter
    private String address;

    @Column(length = 100)
    @Getter
    private String detailAddress;

    public Address(String zipcode, String address, String detailAddress) {
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
    }

    public void allocateUser(User user) {
        this.user = user;
    }

    public void update(Address request) {
        if (request.zipcode != null) {
            this.zipcode = request.zipcode;
        }
        if (request.address != null) {
            this.address = request.address;
        }
        if (request.detailAddress != null) {
            this.detailAddress = request.detailAddress;
        }
    }



}
