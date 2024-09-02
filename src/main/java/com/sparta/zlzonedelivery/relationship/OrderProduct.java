package com.sparta.zlzonedelivery.relationship;

import com.sparta.zlzonedelivery.order.entity.Order;
import com.sparta.zlzonedelivery.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order_products")
@SQLDelete(sql = "UPDATE p_order_products SET deleted_at = CURRENT_TIMESTAMP, deleted_by = ? WHERE order_product_id = ?")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_product_id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Getter
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Getter
    private Product product;

    @Column(nullable = false)
    @Getter
    private Integer quantity;

    @Builder
    public OrderProduct(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

}
