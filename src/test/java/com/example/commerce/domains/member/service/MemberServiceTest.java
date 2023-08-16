package com.example.commerce.domains.member.service;

import com.example.commerce.common.value.Address;
import com.example.commerce.domains.member.domain.MemberEntity;
import com.example.commerce.domains.member.domain.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        MemberSignUpRequestDTO request = MemberSignUpRequestDTO.builder()
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
        MemberResponseDTO member = memberService.signUp(request);

        // then
        assertEquals(member.getName(), "Test User");
    }

    @Test
    public void testSignUp_DuplicateMember() {
        // given
        MemberSignUpRequestDTO request = MemberSignUpRequestDTO.builder()
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

        MemberResponseDTO member = memberService.findMember(memberId);

        // then
        assertEquals("Test User", member.getName());
    }

    @Test
    public void testFindMember_NonExistentMember() {
        Long memberId = 1L;

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> memberService.findMember(memberId));
    }


}
