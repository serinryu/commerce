package com.example.commerce.domains.member.presentation;

import com.example.commerce.domains.member.service.MemberResponseDTO;
import com.example.commerce.domains.member.service.MemberService;
import com.example.commerce.domains.member.service.MemberSignUpRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/member/signup")
    public ResponseEntity<MemberResponseDTO> signUp(@RequestBody @Valid MemberSignUpRequestDTO request){
        MemberResponseDTO response = memberService.signUp(request);
        return ResponseEntity.ok().body(response);
    }

    // 회원 조회
    @GetMapping("/member/{id}")
    public ResponseEntity<MemberResponseDTO> findMember(@PathVariable Long id){
        MemberResponseDTO response = memberService.findMember(id);
        return ResponseEntity.ok().body(response);
    }
}
