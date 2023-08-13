package com.example.commerce.common.config;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 각각의 Entity들에서 공통적으로 필요한 필드들이 있음
// JpaAudit은 각각의 Entity의 생성시간과, 수정시간을 자동으로 생성 및 갱신해줌
// 이 BaseEntity를 상속받는 Entity들은 createdDate, lastModifiedDate가 자동으로 기록
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreatedBy
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
