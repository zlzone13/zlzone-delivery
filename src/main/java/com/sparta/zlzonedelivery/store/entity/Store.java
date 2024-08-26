package com.sparta.zlzonedelivery.store.entity;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import com.sparta.zlzonedelivery.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_stores")
@SQLDelete(sql = "UPDATE p_stores SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //user_id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //category_id

    //store_location_id

    @Column(name = "store_name", length = 100)
    private String storeName;

    @Column
    private String description;

    @Column
    private String announcement;

    @Column(name = "b_no", length = 20)
    private String bNo;

    @Column(name = "telephone_no", length = 20)
    private String telephoneNo;

    @Column(name = "delivery_area", length = 100)
    private String deliveryArea;

    @Column(name = "open_close_time")
    private String openCloseTime;

    @Column(name = "country_info")
    private String countryInfo;

    @Builder
    protected Store(String storeName, String description, String announcement, String bNo,
                    String telephoneNo, String deliveryArea, String openCloseTime,
                    String countryInfo) {
        this.storeName = storeName;
        this.description = description;
        this.announcement = announcement;
        this.bNo = bNo;
        this.telephoneNo = telephoneNo;
        this.deliveryArea = deliveryArea;
        this.openCloseTime = openCloseTime;
        this.countryInfo = countryInfo;
    }

}

