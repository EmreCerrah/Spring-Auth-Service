package com.emrecerrah.springauthservice.repository;

import com.emrecerrah.springauthservice.constant.ERole;
import com.emrecerrah.springauthservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
