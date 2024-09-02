package com.sparta.zlzonedelivery.payment.service.dto;

import com.sparta.zlzonedelivery.payment.entity.PaymentStatus;

public record PaymentUpdateRequestDto(

        String pgId,
        PaymentStatus paymentStatus

) {

}
