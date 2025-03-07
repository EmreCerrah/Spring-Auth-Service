package com.emrecerrah.springauthservice.repository;

import com.emrecerrah.springauthservice.constant.ERole;
import com.emrecerrah.springauthservice.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IRoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
