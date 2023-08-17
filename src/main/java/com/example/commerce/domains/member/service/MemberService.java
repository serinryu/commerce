package com.example.commerce.domains.member.service;

import com.example.commerce.common.value.Address;
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
