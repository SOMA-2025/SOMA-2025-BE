package com.kuad.soma.service;

import com.kuad.soma.entity.Receipt;
import com.kuad.soma.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ReceiptRepository receiptRepository;

    @Transactional(readOnly = true)
    public Page<Receipt> getReceipts(Pageable pageable) {
        return receiptRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Receipt> getReceiptById(Long id) {
        return receiptRepository.findById(id);
    }

    // 전체 조회 메서드 추가
    @Transactional(readOnly = true)
    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAllByOrderByCreatedAtDesc();
    }
}