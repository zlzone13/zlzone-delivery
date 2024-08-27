package com.sparta.zlzonedelivery.category.repository;

import com.sparta.zlzonedelivery.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByCategoryName(String categoryName);

}
