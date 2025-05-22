package com.kuad.soma.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kuad.soma.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receipt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private String accountHolder;

    @Column(nullable = false)
    private String bankName;  // 은행명 필드 추가

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING_PAYMENT;

    @JsonManagedReference
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public void updateTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    // Order 추가 메서드
    public void addOrder(Order order) {
        this.orders.add(order);
        order.setReceipt(this);
    }
}