package com.kuad.soma.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {
    private String accountHolder; // 입금자명
    private String accountNumber; // 계좌번호
    private String phoneNumber;   // 연락처
    private List<OrderRequest> orders; // 주문 아이템 목록
}

