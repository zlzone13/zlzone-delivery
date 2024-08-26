package com.sparta.zlzonedelivery.store.entity;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_stores")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //user_id

    //category_id

    @Column(name = "store_name")
    private String storeName;

    @Column
    private String description;

    @Column
    private String announcement;

    @Column(name = "b_no")
    private String bNo;

    @Column(name = "telephone_no")
    private String telephoneNo;

    @Column(name = "delivery_area")
    private String deliveryArea;

    @Column(name = "open_close_time")
    private String openCloseTime;

    @Column(name = "country_info")
    private String countryInfo;

}

