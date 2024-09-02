package com.sparta.zlzonedelivery.relationship;

import com.sparta.zlzonedelivery.category.entity.Category;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "p_store_categories")
@SQLDelete(sql = "UPDATE p_store_categories SET deleted_at = CURRENT_TIMESTAMP, deleted_by = ? WHERE store_category_id = ?")
public class StoreCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_category_id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    @Getter
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
