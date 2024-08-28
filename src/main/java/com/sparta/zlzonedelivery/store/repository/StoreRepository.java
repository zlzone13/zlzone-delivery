package com.sparta.zlzonedelivery.store.repository;

import com.sparta.zlzonedelivery.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {

    Optional<Store> findByIdAndIsPublicIsTrue(UUID uuid);

    Page<Store> findAllByIsPublicIsTrue(Pageable pageable);

    boolean existsByIdAndIsPublicIsTrue(UUID uuid);

}
