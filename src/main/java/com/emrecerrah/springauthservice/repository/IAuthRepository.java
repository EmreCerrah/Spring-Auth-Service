package com.emrecerrah.springauthservice.repository;

import com.emrecerrah.springauthservice.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAuthRepository extends JpaRepository<Auth, UUID> {

    Optional<Auth> findByUsername(String username);

    Boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}