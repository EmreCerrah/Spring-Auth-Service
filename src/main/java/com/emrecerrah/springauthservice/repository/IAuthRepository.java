package com.emrecerrah.springauthservice.repository;

import com.emrecerrah.springauthservice.model.Auth;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository <Auth, Long> {

    Boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<Auth> findByUsernameAndPassword(String username, String password);
}