package com.sparta.zlzonedelivery.category.entity;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "p_categories")
@SQLDelete(sql = "UPDATE p_categories SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

}
