package com.kuad.soma.dto;

import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamSearchResponse {
    private String teamName;
    private String teamPageUrl;
    private List<MemberSearchResponse> members;

    @Builder
    public TeamSearchResponse(String teamName, String teamPageUrl, List<MemberSearchResponse> members) {
        this.teamName = teamName;
        this.teamPageUrl = teamPageUrl;
        this.members = members;
    }
}
