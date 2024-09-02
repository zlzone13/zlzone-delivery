package com.sparta.zlzonedelivery.payment.repository;

import com.sparta.zlzonedelivery.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

}
