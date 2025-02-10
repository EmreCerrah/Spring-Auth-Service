package com.emrecerrah.springauthservice.model;

import com.emrecerrah.springauthservice.constant.ERole;
import jakarta.persistence.*;
import lombok.*;

@Builder // bir siniftan nesne türetmek için kullanilir
@Data // set ve get metodlarini otomatik tanimlar
@NoArgsConstructor // bos paramtereli hazırlayıcı yapıcı metodu oluşturur.
@AllArgsConstructor // dolu paramtereli hazırlayıcı yapıcı metodu oluşturur.
@ToString // nesne bilgisini terminale yazdirmak icindir
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ERole name;
}

