package com.emrecerrah.springauthservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.emrecerrah.springauthservice.constant.EndPoint.ENDPOINT_SECRET;

/**
 * Controller for testing role-based access control.
 * Provides endpoints to verify access based on user roles.
 */
@CrossOrigin
@RestController
@RequestMapping(ENDPOINT_SECRET)
public class AccessController {

    /**
     * Tests access to dashboard functionality.
     * @return true if user has required roles
     */
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public boolean dashboardAccess() {
        return true;
    }

    /**
     * Tests access to user functionality.
     * @return true if user has required roles
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MOD') or hasRole('ADMIN')")
    public boolean userAccess() {
        return true;
    }

    /**
     * Tests access to moderator functionality.
     * @return true if user has required roles
     */
    @GetMapping("/mod")
    @PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
    public boolean modAccess() {
        return true;
    }

    /**
     * Tests access to admin functionality.
     * @return true if user has required roles
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean adminAccess() {
        return true;
    }
}