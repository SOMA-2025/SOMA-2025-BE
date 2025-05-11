package com.kuad.soma.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {
    private String accountHolder;
    private String bankName;  // 은행명 필드 추가
    private String accountNumber;
    private String phoneNumber;
    private List<OrderRequest> orders;
}