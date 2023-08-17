package com.example.commerce.domains.member.service;

import com.example.commerce.common.value.Address;
import com.example.commerce.domains.member.domain.MemberEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberResponseDTO {
    private Long id;
    private String authId;
    // no authPW
    private String name;
    private String phone;
    private Address address;
    private String role;

    public static MemberResponseDTO fromEntity(MemberEntity memberEntity) {
        MemberResponseDTO dto = new MemberResponseDTO();
        dto.setId(memberEntity.getId());
        dto.setAuthId(memberEntity.getAuthId());
        dto.setName(memberEntity.getName());
        dto.setPhone(memberEntity.getPhone());
        dto.setAddress(memberEntity.getAddress());
        dto.setRole(memberEntity.getRole().toString()); // Convert Role enum to string

        return dto;
    }
}
