package com.example.commerce.domains.member.domain;

import com.example.commerce.common.config.BaseEntity;
import com.example.commerce.common.value.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;
    private String phone;

    @Embedded
    private Address address;

    @Builder
    private MemberEntity(String name, String phone, Address address) {
        this.role = Role.USER;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

}
