package com.sparta.zlzonedelivery.payment.entity;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import com.sparta.zlzonedelivery.order.entity.Order;
import com.sparta.zlzonedelivery.payment.service.dto.PaymentUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_payments")
@SQLDelete(sql = "UPDATE p_payments SET deleted_at = CURRENT_TIMESTAMP, deleted_by = ? WHERE payment_id = ?")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    Order order;

    @Column(nullable = false)
    PaymentStatus paymentStatus;

    @Column(nullable = false)
    String pgId;

    @Builder
    public Payment(Order order, PaymentStatus paymentStatus, String pgId) {
        this.order = order;
        this.paymentStatus = paymentStatus;
        this.pgId = pgId;
    }

    public void updatePayment(PaymentUpdateRequestDto requestDto) {
        if (requestDto.pgId() != null) {
            this.pgId = requestDto.pgId();
        }
        if (requestDto.paymentStatus() != null) {
            this.paymentStatus = requestDto.paymentStatus();
        }
    }

}
