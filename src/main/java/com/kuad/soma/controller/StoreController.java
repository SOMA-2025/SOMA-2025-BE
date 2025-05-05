package com.kuad.soma.controller;

import com.kuad.soma.dto.*;
import com.kuad.soma.entity.Receipt;
import com.kuad.soma.service.StoreService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 모든 상품 조회
     */
    @GetMapping("/items")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> items = storeService.getAllItems();
        return ResponseEntity.ok(items);
    }


    @GetMapping("/items/team/{teamId}")
    public ResponseEntity<List<ItemDTO>> getItemsByTeamId(@PathVariable Long teamId) {
        List<ItemDTO> items = storeService.getItemsByTeamId(teamId);
        return ResponseEntity.ok(items);
    }

    /**
     * 팀 이름으로 상품 조회
     */
    @GetMapping("/items/team/name/{teamName}")
    public ResponseEntity<List<ItemDTO>> getItemsByTeamName(@PathVariable String teamName) {
        List<ItemDTO> items = storeService.getItemsByTeamName(teamName);
        return ResponseEntity.ok(items);
    }

    /**
     * 상품 상세 조회
     */
    @GetMapping("/items/{itemId}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long itemId) {
        Optional<ItemDTO> item = storeService.getItemById(itemId);
        return item.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 주문 생성
     */
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        try {
            Receipt receipt = storeService.createOrder(checkoutRequest);
            ReceiptDTO receiptDTO = ReceiptDTO.from(receipt);
            return ResponseEntity.status(HttpStatus.CREATED).body(receiptDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("주문 처리 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    /**
     * 모든 팀 조회
     */
    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<TeamDTO> teamDTOs = storeService.getAllTeamDTOs();
        return ResponseEntity.ok(teamDTOs);
    }

    /**
     * 주문 조회
     */
    @GetMapping("/receipts/{receiptId}")
    public ResponseEntity<?> getReceiptById(@PathVariable Long receiptId) {
        try {
            Optional<Receipt> receiptOpt = storeService.getReceiptById(receiptId);
            if (receiptOpt.isPresent()) {
                ReceiptDTO receiptDTO = ReceiptDTO.from(receiptOpt.get());
                return ResponseEntity.ok(receiptDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("영수증 정보를 불러오는 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    /**
     * 사용자 주문 내역 조회
     */
    @GetMapping("/receipts/user")
    public ResponseEntity<?> getUserReceipts(@RequestParam String phoneNumber) {
        try {
            List<Receipt> receipts = storeService.getReceiptsByPhoneNumber(phoneNumber);
            List<ReceiptDTO> receiptDTOs = receipts.stream()
                    .map(ReceiptDTO::from)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(receiptDTOs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("주문 내역을 불러오는 중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    // 에러 응답용 클래스
    @Getter
    @AllArgsConstructor
    static class ErrorResponse {
        private String message;
    }
}