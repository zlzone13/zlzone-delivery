package com.sparta.zlzonedelivery.location.repository;

import com.sparta.zlzonedelivery.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {

}
