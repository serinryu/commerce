package com.example.commerce.domains.member.service;

import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
public class MemberSignUpRequestDTO {
    @NotBlank
    private String authId;
    @NotBlank
    private String authPw;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
}