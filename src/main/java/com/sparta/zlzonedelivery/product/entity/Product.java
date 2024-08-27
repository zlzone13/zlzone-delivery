package com.sparta.zlzonedelivery.product.entity;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import com.sparta.zlzonedelivery.store.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Entity
@Table(name = "p_products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE p_products SET deleted_at = CURRENT_TIMESTAMP, is_public = false WHERE id = ?")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;

    @Column
    @Getter
    private String name;

    @Column
    @Getter
    private String description;

    @Column
    @Getter
    private Integer price;

    @Column(name = "is_shown")
    private Boolean shown = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @Getter
    private Store store;

    @Builder
    public Product(String name, String description, Integer price, Store store) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.store = store;
    }


    public void updateProduct(String name, String description, Integer price, Boolean isShown) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.shown = isShown;
    }

}
