package com.kuad.soma.service;

import com.kuad.soma.dto.MemberSearchResponse;
import com.kuad.soma.entity.Member;
import com.kuad.soma.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private static final String CLOUDFRONT_DOMAIN = "https://images.kuad-archive.com";
    private static final String PORTFOLIO_BASE_URL = "/2025/portfolio";

    public List<MemberSearchResponse> searchMembers(String keyword) {
        return memberRepository.searchByNameContaining(keyword).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MemberSearchResponse convertToDto(Member member) {
        return MemberSearchResponse.builder()
                .name(member.getName())
                .profileImageUrl(CLOUDFRONT_DOMAIN + "/" + member.getProfileImageKey())
                .portfolioUrl(PORTFOLIO_BASE_URL + "/" + member.getPortfolioName())
                .teamName(member.getTeamName())
                .build();
    }
}
