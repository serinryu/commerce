package com.example.commerce.domains.member.service;

import com.example.commerce.common.value.Address;
import com.example.commerce.domains.member.domain.MemberEntity;
import com.example.commerce.domains.member.domain.MemberRepository;
import com.example.commerce.domains.member.exception.AlreadySignUpUserException;
import com.example.commerce.domains.member.exception.UserNotFoundException;
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
        MemberSignUpRequestDTO request = createMemberSignUpRequestDTO();

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
        MemberSignUpRequestDTO request = createMemberSignUpRequestDTO();

        MemberEntity existingMember = createMemberEntity();

        // when
        // 중복 회원 존재
        when(memberRepository.findFirstByAuthId(request.getAuthId())).thenReturn(Optional.of(existingMember));

        // then
        assertThrows(AlreadySignUpUserException.class, () -> memberService.signUp(request));
        verify(memberRepository, never()).save(any(MemberEntity.class));
    }


    @Test
    public void testFindMember() {
        // given
        MemberEntity existingMember = createMemberEntity();
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

        assertThrows(UserNotFoundException.class, () -> memberService.findMember(memberId));
    }

    private String authId = "testuser";
    private String authPw = "password";
    private String name = "Test User";
    private String phone = "123-456-7890";
    private String city = "Test City";
    private String street = "Test Street";

    private MemberSignUpRequestDTO createMemberSignUpRequestDTO(){
        return MemberSignUpRequestDTO.builder()
                .authId(authId)
                .authPw(authPw)
                .name(name)
                .phone(phone)
                .city(city)
                .street(street)
                .build();
    }

    private MemberEntity createMemberEntity(){
        return MemberEntity.builder()
                .authId(authId)
                .authPw(authPw)
                .name(name)
                .phone(phone)
                .address(new Address(city, street))
                .build();
    }

}
