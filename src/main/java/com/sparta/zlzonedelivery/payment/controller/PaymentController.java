package com.sparta.zlzonedelivery.payment.controller;

import com.sparta.zlzonedelivery.payment.service.PaymentService;
import com.sparta.zlzonedelivery.payment.service.dto.PaymentCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/orders/{orderId}/payments")
    public ResponseEntity<Void> createPayment(@PathVariable UUID orderId,
                                              @RequestBody PaymentCreateRequestDto requestDto) {
        paymentService.createPayment(orderId, requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
