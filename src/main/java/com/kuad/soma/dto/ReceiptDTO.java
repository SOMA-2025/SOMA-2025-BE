package com.kuad.soma.dto;

import com.kuad.soma.entity.Receipt;
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
    private String bankName;          // 은행명 필드 추가
    private String accountNumber;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private List<OrderDTO> orders;

    public static ReceiptDTO from(Receipt receipt) {
        List<OrderDTO> orderDTOs = receipt.getOrders().stream()
                .map(OrderDTO::from)
                .collect(Collectors.toList());

        return ReceiptDTO.builder()
                .id(receipt.getId())
                .totalAmount(receipt.getTotalAmount())
                .accountHolder(receipt.getAccountHolder())
                .bankName(receipt.getBankName())
                .accountNumber(receipt.getAccountNumber())
                .phoneNumber(receipt.getPhoneNumber())
                .createdAt(receipt.getCreatedAt())
                .orders(orderDTOs)
                .build();
    }
}
