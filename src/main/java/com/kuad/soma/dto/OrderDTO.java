package com.kuad.soma.dto;

import com.kuad.soma.entity.Order;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long itemId;
    private String itemName;
    private String itemImagePath;
    private String creator;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;

    public static OrderDTO from(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .itemId(order.getItem().getId())
                .itemName(order.getItem().getName())
                .itemImagePath(order.getItem().getItemImagePath())
                .creator(order.getItem().getCreator())
                .price(order.getItem().getPrice())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}