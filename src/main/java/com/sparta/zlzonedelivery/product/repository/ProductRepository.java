package com.sparta.zlzonedelivery.product.repository;

import com.sparta.zlzonedelivery.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findByIdAndIsPublicIsTrue(UUID productId);

    Page<Product> findAllByIsPublicIsTrue(Pageable pageable);

}
