package com.kuad.soma.service;

import com.kuad.soma.dto.MemberSearchResponse;
import com.kuad.soma.dto.TeamSearchResponse;
import com.kuad.soma.entity.Member;
import com.kuad.soma.entity.Team;
import com.kuad.soma.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private static final String CLOUDFRONT_DOMAIN = "https://kuad-archive.com";
    private static final String PORTFOLIO_BASE_URL = "/2025/portfolio";
    private static final String TEAM_PAGE_BASE_URL = "/2025/team";

    public List<TeamSearchResponse> searchMembers(String keyword) {
        List<Member> members;

        if (keyword == null || keyword.trim().isEmpty()) {
            members = memberRepository.findAllWithTeam();
        } else {
            members = memberRepository.searchByNameContaining(keyword);
        }

        Map<Team, List<Member>> teamMembersMap = members.stream()
                .collect(Collectors.groupingBy(Member::getTeam));

        return teamMembersMap.entrySet().stream()
                .map(entry -> createTeamSearchResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private TeamSearchResponse createTeamSearchResponse(Team team, List<Member> members) {
        return TeamSearchResponse.builder()
                .teamName(team.getName())
                .teamPageUrl(team.getPageKey())
                .members(members.stream()
                        .map(this::convertToMemberDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private MemberSearchResponse convertToMemberDto(Member member) {
        return MemberSearchResponse.builder()
                .name(member.getName())
                .profileImageUrl(member.getProfileImageKey())
                .portfolioUrl(member.getPortfolioName())
                .build();
    }
}