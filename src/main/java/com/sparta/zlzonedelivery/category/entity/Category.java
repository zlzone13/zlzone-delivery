package com.sparta.zlzonedelivery.category.entity;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import com.sparta.zlzonedelivery.relationship.StoreCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_categories")
@SQLDelete(sql = "UPDATE p_categories SET deleted_at = CURRENT_TIMESTAMP, deleted_by = ? WHERE category_id = ?")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id", updatable = false, nullable = false)
    @Getter
    private UUID id;

    @Column(nullable = false, length = 100)
    @Getter
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<StoreCategory> storeCategoryList = new ArrayList<>();

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(UUID id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public Category updateCategory(String categoryName) {
        return new Category(
                this.id,
                categoryName != null ? categoryName : this.categoryName
        );
    }

}
