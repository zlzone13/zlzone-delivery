package com.sparta.zlzonedelivery.store.entity;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import com.sparta.zlzonedelivery.location.entity.Location;
import com.sparta.zlzonedelivery.relationship.StoreCategory;
import com.sparta.zlzonedelivery.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_stores")
@SQLDelete(sql = "UPDATE p_stores SET deleted_at = CURRENT_TIMESTAMP, is_public = false WHERE id = ?")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<StoreCategory> storeCategoryList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_location_id")
    private Location location;

    @Column(name = "store_name", length = 100)
    @Getter
    private String storeName;

    @Column
    @Getter
    private String description;

    @Column
    @Getter
    private String announcement;

    @Column(name = "b_no", length = 20)
    @Getter
    private String bNo;

    @Column(name = "telephone_no", length = 20)
    @Getter
    private String telephoneNo;

    @Column(name = "delivery_area", length = 100)
    @Getter
    private String deliveryArea;

    @Column(name = "open_close_time")
    @Getter
    private String openCloseTime;

    @Column(name = "country_info")
    @Getter
    private String countryInfo;

    @Builder
    protected Store(String storeName, String description, String announcement, String bNo,
                    String telephoneNo, String deliveryArea, String openCloseTime,
                    String countryInfo, User user, Location location) {
        this.storeName = storeName;
        this.description = description;
        this.announcement = announcement;
        this.bNo = bNo;
        this.telephoneNo = telephoneNo;
        this.deliveryArea = deliveryArea;
        this.openCloseTime = openCloseTime;
        this.countryInfo = countryInfo;
        this.user = user;
        this.location = location;
    }

    public void updateStore(String description, String announcement, String telephoneNo,
                            String deliveryArea, String openCloseTime, String countryInfo) {
        if (description != null) this.description = description;
        if (announcement != null) this.announcement = announcement;
        if (telephoneNo != null) this.telephoneNo = telephoneNo;
        if (deliveryArea != null) this.deliveryArea = deliveryArea;
        if (openCloseTime != null) this.openCloseTime = openCloseTime;
        if (countryInfo != null) this.countryInfo = countryInfo;
    }

}

