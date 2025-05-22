package com.kuad.soma.dto;

import com.kuad.soma.entity.Receipt;
import com.kuad.soma.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDTO {
    private Long id;
    private BigDecimal totalAmount;
    private String accountHolder;
    private String bankName;
    private String accountNumber;
    private String phoneNumber;
    private OrderStatus status;
    private String statusDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderDTO> orders;

    public static ReceiptDTO from(Receipt receipt) {
        return ReceiptDTO.builder()
                .id(receipt.getId())
                .totalAmount(receipt.getTotalAmount())
                .accountHolder(receipt.getAccountHolder())
                .bankName(receipt.getBankName())
                .accountNumber(receipt.getAccountNumber())
                .phoneNumber(receipt.getPhoneNumber())
                .status(receipt.getStatus())
                .statusDescription(receipt.getStatus().getDescription())
                .createdAt(receipt.getCreatedAt())
                .updatedAt(receipt.getUpdatedAt())
                .orders(receipt.getOrders().stream()
                        .map(OrderDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }
}