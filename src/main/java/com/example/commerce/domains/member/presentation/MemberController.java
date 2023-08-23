package com.example.commerce.domains.member.presentation;

import com.example.commerce.common.exception.SuccessCode;
import com.example.commerce.common.exception.response.SuccessResponse;
import com.example.commerce.domains.item.service.ItemResponseDTO;
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
    public ResponseEntity<SuccessResponse<MemberResponseDTO>> signUp(@RequestBody @Valid MemberSignUpRequestDTO request){
        MemberResponseDTO data = memberService.signUp(request);
        return ResponseEntity
                .status(SuccessCode.CREATE_SUCCESS.getStatus())
                .body(SuccessResponse.of(SuccessCode.CREATE_SUCCESS, data));
    }

    // 회원 조회
    @GetMapping("/member/{id}")
    public ResponseEntity<SuccessResponse<MemberResponseDTO>> findMember(@PathVariable Long id){
        MemberResponseDTO data = memberService.findMember(id);
        return ResponseEntity
                .status(SuccessCode.GET_INFO_SUCCESS.getStatus())
                .body(SuccessResponse.of(SuccessCode.GET_INFO_SUCCESS, data));
    }
}
