package com.kuad.soma.controller;

import com.kuad.soma.entity.Receipt;
import com.kuad.soma.service.AdminService;
import com.kuad.soma.service.ExcelExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    @Value("${admin.password}")
    private String adminPassword;

    private final AdminService adminService;
    private final ExcelExportService excelExportService;

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String,String> creds) {
        if (adminPassword.equals(creds.get("password"))) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Login successful"));
        }
        return ResponseEntity.status(401).body(Map.of("success", false, "message", "Invalid password"));
    }

    // JSON 직렬화 강제
    @GetMapping(value = "/receipts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReceipts(
            @RequestParam(required=false) Integer page,
            @RequestParam(required=false) Integer size
    ) {
        if (page == null) {
            List<Receipt> all = adminService.getAllReceipts();
            // 리스트를 JSON 배열로 반환
            return ResponseEntity.ok(all);
        }

        Page<Receipt> pg = adminService.getReceipts(
                PageRequest.of(page, size!=null ? size : 20)
        );
        return ResponseEntity.ok(pg);
    }

    @GetMapping("/receipts/{id}")
    public ResponseEntity<Receipt> getReceiptById(@PathVariable Long id) {
        return adminService.getReceiptById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/receipts/export")
    public ResponseEntity<byte[]> exportReceiptsToExcel(
            @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        try {
            ByteArrayOutputStream out = excelExportService.exportReceiptsToExcel(startDate, endDate);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=receipts_"+LocalDate.now()+".xlsx")
                    .body(out.toByteArray());
        } catch(Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}