package com.sparta.zlzonedelivery.payment.repository;

import com.sparta.zlzonedelivery.payment.entity.Payment;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT p FROM Payment p JOIN p.order o WHERE o.user.id = :userId")
    Page<Payment> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

}
