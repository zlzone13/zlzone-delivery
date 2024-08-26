package com.sparta.zlzonedelivery.store.repository;

import com.sparta.zlzonedelivery.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {

    Optional<Store> findByIdAndIsPublicIsTrue(UUID uuid);

}
