package com.kuad.soma.repository;

import com.kuad.soma.entity.Receipt;
import com.kuad.soma.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    // 기존 메소드들
    List<Receipt> findAllByOrderByCreatedAtDesc();
    List<Receipt> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Receipt> findByPhoneNumberOrderByCreatedAtDesc(String phoneNumber);

    // AdminService에서 사용하는 메소드들 추가
    Page<Receipt> findByStatusNotOrderByCreatedAtDesc(OrderStatus status, Pageable pageable);
    List<Receipt> findByStatusNotOrderByCreatedAtDesc(OrderStatus status);

    // 엑셀 내보내기용 메소드들
    @Query("SELECT r FROM Receipt r WHERE r.status IN ('PENDING_PAYMENT', 'PAYMENT_COMPLETED') ORDER BY r.createdAt DESC")
    List<Receipt> findActiveReceiptsOrderByCreatedAtDesc();

    @Query("SELECT r FROM Receipt r WHERE r.createdAt BETWEEN :startDate AND :endDate AND r.status IN ('PENDING_PAYMENT', 'PAYMENT_COMPLETED') ORDER BY r.createdAt DESC")
    List<Receipt> findActiveReceiptsByCreatedAtBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}