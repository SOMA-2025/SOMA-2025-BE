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

    // 특정 기간 내의 주문 내역 조회
    @Query("SELECT r FROM Receipt r WHERE r.createdAt BETWEEN :startDate AND :endDate ORDER BY r.createdAt DESC")
    List<Receipt> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // 최근 주문 내역 조회 (최신순)
    List<Receipt> findTop10ByOrderByCreatedAtDesc();

    // 특정 입금자명으로 주문 내역 조회
    List<Receipt> findByAccountHolderContainingOrderByCreatedAtDesc(String accountHolder);
}