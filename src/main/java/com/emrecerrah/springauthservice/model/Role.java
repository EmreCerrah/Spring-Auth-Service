package com.emrecerrah.springauthservice.model;

import com.emrecerrah.springauthservice.constant.ERole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "role")
public class Role {

    @Id
    private String id;

    private ERole name;
}
