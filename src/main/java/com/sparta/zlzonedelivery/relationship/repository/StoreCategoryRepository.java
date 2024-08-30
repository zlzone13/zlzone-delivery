package com.sparta.zlzonedelivery.relationship.repository;

import com.sparta.zlzonedelivery.relationship.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, UUID> {

}
