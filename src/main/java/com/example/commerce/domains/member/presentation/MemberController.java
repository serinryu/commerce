package com.example.commerce.domains.member.presentation;

import com.example.commerce.domains.member.service.MemberResponseDTO;
import com.example.commerce.domains.member.service.MemberService;
import com.example.commerce.domains.member.service.MemberSignUpRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/auth/signup")
    public ResponseEntity<MemberResponseDTO> signUp(@RequestBody MemberSignUpRequestDTO request){
        MemberResponseDTO response = memberService.signUp(request);
        return ResponseEntity.ok().body(response);
    }
}
