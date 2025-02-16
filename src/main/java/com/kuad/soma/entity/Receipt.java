package com.kuad.soma.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private String accountNumber;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "receipt")
    private List<Order> orders = new ArrayList<>();
}
