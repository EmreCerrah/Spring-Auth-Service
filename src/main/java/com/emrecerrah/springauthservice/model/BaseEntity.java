package com.emrecerrah.springauthservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEntity {

    @CreatedDate
    private Instant createAt; // Oluşturulma zamanı

    @LastModifiedDate
    private Instant updateAt; // Güncellenme zamanı

    @Builder.Default
    private boolean state = true;
}