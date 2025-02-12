package com.emrecerrah.springauthservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
//TODO: kontrol et!
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false) // set just fist time
    private Long createAt;

    @LastModifiedDate
    private Long updateAt;

    @Builder.Default
    private boolean state = Boolean.TRUE;

}