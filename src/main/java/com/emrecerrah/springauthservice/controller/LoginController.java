package com.emrecerrah.springauthservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.emrecerrah.springauthservice.constant.EndPoint.ENDPOINT_SECRET;

@CrossOrigin
@RestController
@RequestMapping(ENDPOINT_SECRET)
public class LoginController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public boolean dashboardAccess() {
        return true;
    }

//    @PostMapping("/role")
//    @PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
//    public ResponseEntity<Boolean> giveRoleAccess(@Valid @RequestBody RoleRequestDTO dto) { //TODO: DTO incele
//        return ResponseEntity.ok(authService.AddRole(dto));
//    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MOD') or hasRole('ADMIN')")
    public boolean userAccess() {
        return true;
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public boolean modAccess() {
        return true;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean adminAccess() {
        return true;
    }

}