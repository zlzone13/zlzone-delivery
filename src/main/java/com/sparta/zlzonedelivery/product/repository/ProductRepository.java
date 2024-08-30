package com.sparta.zlzonedelivery.product.repository;

import com.sparta.zlzonedelivery.product.entity.Product;
import com.sparta.zlzonedelivery.product.entity.ProductCategory;
import com.sparta.zlzonedelivery.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findByIdAndIsPublicIsTrue(UUID productId);

    Page<Product> findAllByIsPublicIsTrueOrderByPrice(Pageable pageable);

    Page<Product> findAllByStoreAndIsPublicIsTrueOrderByPrice(Store store, Pageable pageable);

    Page<Product> searchByProductCategoryAndIsPublicIsTrueOrderByPrice(ProductCategory productCategory, Pageable pageable);

}
