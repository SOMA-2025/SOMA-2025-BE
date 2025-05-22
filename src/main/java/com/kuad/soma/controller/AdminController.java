package com.kuad.soma.controller;

import com.kuad.soma.dto.PageResponseDto;
import com.kuad.soma.dto.ReceiptDTO;
import com.kuad.soma.entity.Receipt;
import com.kuad.soma.enums.OrderStatus;
import com.kuad.soma.service.AdminService;
import com.kuad.soma.service.ExcelExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    @Value("${admin.password}")
    private String adminPassword;

    private final AdminService adminService;
    private final ExcelExportService excelExportService;

    /**
     * 관리자 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> creds) {
        String password = creds.get("password");
        if (adminPassword.equals(password)) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Login successful"));
        }
        return ResponseEntity.status(401)
                .body(Map.of("success", false, "message", "Invalid password"));
    }

    /**
     * 주문 내역 페이징 조회 (DTO) - 삭제되지 않은 것만
     */
    @GetMapping(value = "/receipts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseDto<ReceiptDTO>> getReceipts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        int p = (page == null ? 0 : page);
        int s = (size == null ? 20 : size);

        Page<Receipt> receiptPage = adminService.getReceipts(PageRequest.of(p, s));

        List<ReceiptDTO> dtoList = receiptPage.getContent().stream()
                .map(ReceiptDTO::from)
                .collect(Collectors.toList());

        PageResponseDto<ReceiptDTO> response = new PageResponseDto<>(
                dtoList,
                receiptPage.getTotalPages(),
                receiptPage.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * 단일 주문 상세 조회 (DTO)
     */
    @GetMapping(value = "/receipts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReceiptDTO> getReceiptById(@PathVariable Long id) {
        return adminService.getReceiptById(id)
                .map(ReceiptDTO::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 주문 상태 토글 (입금 대기 ↔ 입금 완료)
     */
    @PutMapping("/receipts/{id}/toggle-status")
    public ResponseEntity<ReceiptDTO> toggleReceiptStatus(@PathVariable Long id) {
        try {
            Receipt updatedReceipt = adminService.toggleReceiptStatus(id);
            return ResponseEntity.ok(ReceiptDTO.from(updatedReceipt));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 주문 삭제 (소프트 삭제)
     */
    @DeleteMapping("/receipts/{id}")
    public ResponseEntity<Map<String, Object>> deleteReceipt(@PathVariable Long id) {
        try {
            Receipt deletedReceipt = adminService.deleteReceipt(id);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "주문이 삭제되었습니다.",
                    "receipt", ReceiptDTO.from(deletedReceipt)
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Excel 내보내기 (입금 대기, 입금 완료 상태만)
     */
    @GetMapping("/receipts/export")
    public ResponseEntity<byte[]> exportReceiptsToExcel(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            ByteArrayOutputStream out = excelExportService.exportReceiptsToExcel(startDate, endDate);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=receipts_" + LocalDate.now() + ".xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(out.toByteArray());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}