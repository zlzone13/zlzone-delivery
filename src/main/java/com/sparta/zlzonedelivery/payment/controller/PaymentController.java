package com.sparta.zlzonedelivery.payment.controller;

import com.sparta.zlzonedelivery.global.auth.security.UserDetailsImpl;
import com.sparta.zlzonedelivery.payment.service.PaymentService;
import com.sparta.zlzonedelivery.payment.service.dto.PaymentCreateRequestDto;
import com.sparta.zlzonedelivery.payment.service.dto.PaymentGetResponseDto;
import com.sparta.zlzonedelivery.payment.service.dto.PaymentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @GetMapping("payments")
    public ResponseEntity<Page<PaymentGetResponseDto>> getPaymentList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                      @PageableDefault Pageable pageable) {
        Page<PaymentGetResponseDto> payments = paymentService.getPaymentList(userDetails, pageable);
        return new ResponseEntity<>(payments, HttpStatus.OK);

    }

    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<PaymentGetResponseDto> getPayment(@PathVariable UUID paymentId) {
        PaymentGetResponseDto responseDto = paymentService.getPayment(paymentId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/payments/{paymentId}")
    @Secured("MASTER")
    public ResponseEntity<Void> updatePayment(@PathVariable UUID paymentId, @RequestBody PaymentUpdateRequestDto requestDto) {
        paymentService.updatePayment(paymentId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
