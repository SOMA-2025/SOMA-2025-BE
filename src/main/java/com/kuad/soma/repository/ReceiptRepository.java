package com.kuad.soma.repository;

import com.kuad.soma.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    // 특정 사용자의 모든 주문 내역 조회 (전화번호로 식별)
    List<Receipt> findByPhoneNumberOrderByCreatedAtDesc(String phoneNumber);

    // ReceiptRepository에 추가
    List<Receipt> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    // 최근 주문 내역 조회 (최신순)
    List<Receipt> findTop10ByOrderByCreatedAtDesc();

    // 특정 입금자명으로 주문 내역 조회
    List<Receipt> findByAccountHolderContainingOrderByCreatedAtDesc(String accountHolder);

    // 메서드 추가
    List<Receipt> findAllByOrderByCreatedAtDesc();

}