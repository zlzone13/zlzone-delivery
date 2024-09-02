package com.sparta.zlzonedelivery.payment.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.zlzonedelivery.payment.entity.Payment;
import com.sparta.zlzonedelivery.payment.entity.PaymentStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record PaymentGetResponseDto(

        UUID paymentId,
        UUID orderId,
        Long userId,
        Integer amount,
        String pgId,
        PaymentStatus paymentStatus

) {

    public static PaymentGetResponseDto fromEntity(Payment payment) {
        return PaymentGetResponseDto.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrder().getId())
                .userId(payment.getOrder().getUser().getId())
                .amount(payment.getOrder().getTotalAmount())
                .pgId(payment.getPhId())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }

}
