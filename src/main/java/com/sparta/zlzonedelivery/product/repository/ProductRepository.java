package com.sparta.zlzonedelivery.product.repository;

import com.sparta.zlzonedelivery.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
