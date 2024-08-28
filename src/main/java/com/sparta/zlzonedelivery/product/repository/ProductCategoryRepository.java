package com.sparta.zlzonedelivery.product.repository;

import com.sparta.zlzonedelivery.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    Optional<ProductCategory> findByProductCategoryNameAndIsPublicIsTrue(String categoryName);

}
