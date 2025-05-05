package com.kuad.soma.dto;

import com.kuad.soma.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private BigDecimal price;
    private String creator;
    private String descriptionImagePath;
    private String itemImagePath;
    private Long teamId;
    private String teamName;

    public static ItemDTO from(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .name(item.getName())
                .price(item.getPrice())
                .creator(item.getCreator())
                .descriptionImagePath(item.getDescriptionImagePath())
                .itemImagePath(item.getItemImagePath())
                .teamId(item.getTeam().getId())
                .teamName(item.getTeam().getName())
                .build();
    }
}
