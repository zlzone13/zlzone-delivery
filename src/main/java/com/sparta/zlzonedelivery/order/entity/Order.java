package com.sparta.zlzonedelivery.order.entity;

import com.sparta.zlzonedelivery.global.entity.BaseEntity;
import com.sparta.zlzonedelivery.order.service.dto.OrderUpdateOfflineRequestDto;
import com.sparta.zlzonedelivery.order.service.dto.OrderUpdateOnlineRequestDto;
import com.sparta.zlzonedelivery.relationship.OrderProduct;
import com.sparta.zlzonedelivery.store.entity.Store;
import com.sparta.zlzonedelivery.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_orders")
@SQLDelete(sql = "UPDATE p_orders SET deleted_at = CURRENT_TIMESTAMP, deleted_by = ? WHERE order_id = ?")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", updatable = false, nullable = false)
    @Getter
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Getter
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "address_id")
//    private Address address;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "payment_id")
//    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @Getter
    private Store store;

    @Column(nullable = false)
    @Getter
    private OrderType orderType;

    @Column(length = 60)
    @Getter
    private String toRiderRequest;

    @Column(length = 60)
    @Getter
    private String toOwnerRequest;

    @Column
    @Getter
    private Integer totalAmount;

    @Column
    @Getter
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    public Order(/*Address address, Payment payment, */User user, Store store, OrderType orderType, String toRiderRequest, String toOwnerRequest, Integer totalAmount, OrderStatus orderStatus) {
//        this.address = address;
//        this.payment = payment;
        this.user = user;
        this.store = store;
        this.orderType = orderType;
        this.toRiderRequest = toRiderRequest;
        this.toOwnerRequest = toOwnerRequest;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }

    public Order(UUID id, String toRiderRequest, String toOwnerRequest) {
        this.id = id;
        this.toRiderRequest = toRiderRequest;
        this.toOwnerRequest = toOwnerRequest;
    }

    public Order(UUID id, String toOwnerRequest) {
        this.id = id;
        this.toOwnerRequest = toOwnerRequest;
    }

    public Order(UUID id, OrderStatus orderStatus) {
        this.id = id;
        this.orderStatus = orderStatus;
    }

    public void addOrderProduct(OrderProduct orderProduct) {

        this.orderProducts.add(orderProduct);
    }

    public void updateOrderOnline(OrderUpdateOnlineRequestDto requestDto) {
        if (requestDto.toRiderRequest() != null) {
            this.toRiderRequest = requestDto.toRiderRequest();
        }
        if (requestDto.toOwnerRequest() != null) {
            this.toOwnerRequest = requestDto.toOwnerRequest();
        }
    }

    public void updateOrderOffline(OrderUpdateOfflineRequestDto requestDto) {
        if (requestDto.toOwnerRequest() != null) {
            this.toOwnerRequest = requestDto.toOwnerRequest();
        }
    }

    public void cancelOrder(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

}
