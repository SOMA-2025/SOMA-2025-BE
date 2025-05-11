package com.kuad.soma.service;

import com.kuad.soma.entity.Order;
import com.kuad.soma.entity.Receipt;
import com.kuad.soma.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelExportService {

    private final ReceiptRepository receiptRepository;

    public ByteArrayOutputStream exportReceiptsToExcel(LocalDate startDate, LocalDate endDate) throws IOException {
        List<Receipt> receipts;

        if (startDate != null && endDate != null) {
            receipts = receiptRepository.findByCreatedAtBetween(
                    startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        } else {
            receipts = receiptRepository.findAll();
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("주문 내역");

            // 헤더 스타일
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // 헤더 행 생성
            Row headerRow = sheet.createRow(0);
            String[] headers = {"주문번호", "주문일시", "입금자명", "은행명", "계좌번호", "연락처",
                    "상품명", "단가", "수량", "소계", "총금액"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // 데이터 행 생성
            int rowNum = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (Receipt receipt : receipts) {
                for (Order order : receipt.getOrders()) {
                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0).setCellValue(receipt.getId());
                    row.createCell(1).setCellValue(receipt.getCreatedAt().format(formatter));
                    row.createCell(2).setCellValue(receipt.getAccountHolder());
                    row.createCell(3).setCellValue(receipt.getBankName());
                    row.createCell(4).setCellValue(receipt.getAccountNumber());
                    row.createCell(5).setCellValue(receipt.getPhoneNumber());
                    row.createCell(6).setCellValue(order.getItem().getName());
                    row.createCell(7).setCellValue(order.getItem().getPrice().doubleValue());
                    row.createCell(8).setCellValue(order.getQuantity());
                    row.createCell(9).setCellValue(order.getTotalPrice().doubleValue());
                    row.createCell(10).setCellValue(receipt.getTotalAmount().doubleValue());
                }
            }

            // 열 너비 자동 조정
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream;
        }
    }
}