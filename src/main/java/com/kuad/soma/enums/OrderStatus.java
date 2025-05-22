package com.kuad.soma.enums;

public enum OrderStatus {
    PENDING_PAYMENT("입금 대기"),
    PAYMENT_COMPLETED("입금 완료"),
    DELETED("삭제");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
