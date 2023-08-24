package com.example.commerce.domains.member.service;

import com.example.commerce.common.value.Address;
import com.example.commerce.domains.cart.service.CartService;
import com.example.commerce.domains.member.domain.MemberEntity;
import com.example.commerce.domains.member.domain.MemberRepository;
import com.example.commerce.domains.member.exception.AlreadySignUpUserException;
import com.example.commerce.domains.member.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final CartService cartService;

    // 회원가입
    public MemberResponseDTO signUp(MemberSignUpRequestDTO request){

        validateDuplicateMember(request.getAuthId());

        // 회원 저장
        MemberEntity newMember = MemberEntity.builder()
                .authId(request.getAuthId())
                .authPw(request.getAuthPw())
                .name(request.getName())
                .phone(request.getPhone())
                .address(new Address(request.getCity(), request.getStreet()))
                .build();
        memberRepository.save(newMember);

        // 회원 생성 시, 장바구니를 같이 생성
        // 단, 이 방법은 MemberService 의 회원가입 로직에서, Member 도메인과 전혀 관계없는 CartService 에 의존하므로 매우 좋지 않은 방법
        // 이후 이벤트 소싱을 이용해 리팩토링 필요
        cartService.createCart(newMember.getId());

        // Entity to DTO
        MemberResponseDTO newMemberDto = MemberResponseDTO.fromEntity(newMember);

        return newMemberDto;
    }

    private void validateDuplicateMember(String authId){
        Optional<MemberEntity> existingMember = memberRepository.findFirstByAuthId(authId);
        if(existingMember.isPresent()){
            throw AlreadySignUpUserException.EXCEPTION;
        }
    }

    // 회원 찾기
    public MemberResponseDTO findMember(Long id){
        MemberEntity member = validateExistMember(memberRepository.findById(id));
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.fromEntity(member);
        return memberResponseDTO;
    }

    private MemberEntity validateExistMember(Optional<MemberEntity> memberEntity) {
        if(!memberEntity.isPresent())
            throw UserNotFoundException.EXCEPTION;
        return memberEntity.get();
    }

}
