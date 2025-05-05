// TeamDTO.java
package com.kuad.soma.dto;

import com.kuad.soma.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String pageKey;

    public static TeamDTO from(Team team) {
        return TeamDTO.builder()
                .id(team.getId())
                .createdAt(team.getCreatedAt())
                .updatedAt(team.getUpdatedAt())
                .name(team.getName())
                .pageKey(team.getPageKey())
                .build();
    }
}