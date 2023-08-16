package com.example.commerce.domains.member.service;

import com.example.commerce.common.value.Address;
import com.example.commerce.domains.member.domain.MemberEntity;
import com.example.commerce.domains.member.domain.MemberRepository;
import com.example.commerce.domains.member.service.MemberService;
import com.example.commerce.domains.member.service.SignUpRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void testSignUp() {
        // given
        SignUpRequestDTO request = SignUpRequestDTO.builder()
                .authId("testuser")
                .authPw("password")
                .name("Test User")
                .phone("123-456-7890")
                .city("Test City")
                .street("Test Street")
                .build();

        // when
        // 중복 회원 존재하지 않음
        when(memberRepository.findFirstByAuthId(request.getAuthId())).thenReturn(Optional.empty());
        String memberName = memberService.signUp(request);

        // then
        assertEquals(memberName, "Test User");
    }

    @Test
    public void testSignUp_DuplicateMember() {
        // given
        SignUpRequestDTO request = SignUpRequestDTO.builder()
                .authId("testuser")
                .authPw("password")
                .name("Test User")
                .phone("123-456-7890")
                .city("Test City")
                .street("Test Street")
                .build();

        MemberEntity existingMember = MemberEntity.builder()
                .authId("testuser")
                .authPw("password")
                .name("Test User")
                .phone("123-456-7890")
                .address(new Address("Test City", "Test Street"))
                .build();

        // when
        // 중복 회원 존재
        when(memberRepository.findFirstByAuthId(request.getAuthId())).thenReturn(Optional.of(existingMember));

        // then
        assertThrows(IllegalStateException.class, () -> memberService.signUp(request));
        verify(memberRepository, never()).save(any(MemberEntity.class));
    }


    @Test
    public void testFindMember() {
        // given
        MemberEntity existingMember = MemberEntity.builder()
                .authId("testuser")
                .authPw("password")
                .name("Test User")
                .phone("123-456-7890")
                .address(new Address("Test City", "Test Street"))
                .build();
        Long memberId = existingMember.getId();

        // when
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        String foundMemberName = memberService.findMember(memberId);

        // then
        assertEquals("Test User", foundMemberName);
    }

    @Test
    public void testFindMember_NonExistentMember() {
        Long memberId = 1L;

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> memberService.findMember(memberId));
    }

    @Test
    public void testFindAllMember() {
        // given
        List<MemberEntity> memberList = new ArrayList<>();
        MemberEntity member = MemberEntity.builder()
                .authId("testuser")
                .authPw("password")
                .name("Test User")
                .phone("123-456-7890")
                .address(new Address("Test City", "Test Street"))
                .build();
        memberList.add(member);

        // when
        when(memberRepository.findAll()).thenReturn(memberList);

        List<String> memberNameList = memberService.findAllMember();

        // then
        assertEquals(1, memberNameList.size());
        assertEquals("Test User", memberNameList.get(0));
    }

}
