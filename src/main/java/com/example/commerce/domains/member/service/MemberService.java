package com.example.commerce.domains.member.service;

import com.example.commerce.common.value.Address;
import com.example.commerce.domains.member.domain.MemberEntity;
import com.example.commerce.domains.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public String signUp(SignUpRequestDTO request){

        // 중복 회원 예외
        /*
        if(memberRepository.findFirstByAuthId(request.getAuthId()).isPresent()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        */
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

        return newMember.getName();
    }

    private void validateDuplicateMember(String authId){
        Optional<MemberEntity> existingMember = memberRepository.findFirstByAuthId(authId);
        if(existingMember.isPresent()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public String findMember(Long id){
        /*
        MemberEntity member = memberRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
         */
        MemberEntity member = validateExistMember(memberRepository.findById(id));
        return member.getName();
    }

    private MemberEntity validateExistMember(Optional<MemberEntity> memberEntity) {
        if(!memberEntity.isPresent())
            throw new IllegalStateException("존재하지 않는 유저입니다.");
        return memberEntity.get();
    }

    public List<String> findAllMember(){
        List<MemberEntity> memberList = memberRepository.findAll();
        List<String> memberNameList = memberList.stream().map(
            m -> m.getName()).toList();
        return memberNameList;
    }

}
