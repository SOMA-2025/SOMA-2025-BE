package com.kuad.soma.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSearchResponse {
    private String name;
    private String profileImageUrl;
    private String portfolioUrl;
    private String teamName;

    @Builder
    public MemberSearchResponse(String name, String profileImageUrl, String portfolioUrl, String teamName) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.portfolioUrl = portfolioUrl;
        this.teamName = teamName;
    }
}
