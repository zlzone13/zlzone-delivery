package com.sparta.zlzonedelivery.category.repository;

import com.sparta.zlzonedelivery.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByCategoryName(String categoryName);

    Page<Category> findByCategoryNameContaining(String keyword, Pageable pageable);

    List<Category> findByCategoryNameIn(List<String> categoryNames);

}
