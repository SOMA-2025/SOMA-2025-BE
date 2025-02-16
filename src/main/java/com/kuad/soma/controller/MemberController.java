package com.kuad.soma.controller;

import com.kuad.soma.dto.MemberSearchResponse;
import com.kuad.soma.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/search")
    public ResponseEntity<List<MemberSearchResponse>> searchMembers(
            @RequestParam(required = false, defaultValue = "") String keyword) {
        List<MemberSearchResponse> results = memberService.searchMembers(keyword);
        return ResponseEntity.ok(results);
    }
}
