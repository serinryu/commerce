package com.example.commerce.domains.member.presentation;

import com.example.commerce.common.value.Address;
import com.example.commerce.domains.member.service.MemberResponseDTO;
import com.example.commerce.domains.member.service.MemberService;
import com.example.commerce.domains.member.service.MemberSignUpRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    public void testSignUp() throws Exception {
        // Given
        MemberSignUpRequestDTO requestDTO = createMemberSignUpRequestDTO();
        MemberResponseDTO responseDTO = createMemberResponseDTO();

        Mockito.when(memberService.signUp(Mockito.any(MemberSignUpRequestDTO.class)))
                .thenReturn(responseDTO);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/member/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.data.name").value(responseDTO.getName()));
    }

    @Test
    public void testFindMember() throws Exception {
        // Given
        MemberResponseDTO responseDTO = createMemberResponseDTO();

        Mockito.when(memberService.findMember(Mockito.anyLong()))
                .thenReturn(responseDTO);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/member/{id}", responseDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.data.name").value(responseDTO.getName()));
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

    private MemberResponseDTO createMemberResponseDTO(){
        return MemberResponseDTO.builder()
                .id(123L)
                .authId(authId)
                .name(name)
                .phone(phone)
                .address(new Address(city, street))
                .role("USER")
                .build();
    }
}