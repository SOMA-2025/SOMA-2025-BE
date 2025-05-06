package com.kuad.soma.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    // Receipt 설정 메서드 추가
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}