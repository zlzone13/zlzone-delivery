package com.sparta.zlzonedelivery.payment.service;

import com.sparta.zlzonedelivery.global.error.CustomException;
import com.sparta.zlzonedelivery.global.error.ErrorCode;
import com.sparta.zlzonedelivery.order.entity.Order;
import com.sparta.zlzonedelivery.order.entity.OrderStatus;
import com.sparta.zlzonedelivery.order.repository.OrderRepository;
import com.sparta.zlzonedelivery.payment.entity.Payment;
import com.sparta.zlzonedelivery.payment.entity.PaymentStatus;
import com.sparta.zlzonedelivery.payment.repository.PaymentRepository;
import com.sparta.zlzonedelivery.payment.service.dto.PaymentCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    @Transactional
    public void createPayment(UUID orderId, PaymentCreateRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new CustomException(ErrorCode.ORDER_ALREADY_PROCESSED);
        }

        Payment payment = Payment.builder()
                .order(order)
                .paymentStatus(PaymentStatus.COMPLETED)
                .phId(requestDto.pgId())
                .build();

        paymentRepository.save(payment);
    }

}
