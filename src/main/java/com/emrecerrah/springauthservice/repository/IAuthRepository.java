package com.emrecerrah.springauthservice.repository;

import com.emrecerrah.springauthservice.model.Auth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends MongoRepository<Auth, String> {

    Optional<Auth> findByUsername(String username);

    Boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}