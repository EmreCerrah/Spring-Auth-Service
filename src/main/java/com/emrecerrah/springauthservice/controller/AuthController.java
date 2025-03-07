package com.emrecerrah.springauthservice.controller;

import com.emrecerrah.springauthservice.payload.request.LoginRequestDTO;
import com.emrecerrah.springauthservice.payload.request.RegisterRequestDTO;
import com.emrecerrah.springauthservice.payload.response.LoginResponseDTO;
import com.emrecerrah.springauthservice.payload.response.RegisterResponseDTO;
import com.emrecerrah.springauthservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.emrecerrah.springauthservice.constant.EndPoint.*;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(ENDPOINT_AUTH)
public class AuthController {
//FIX: daha sonra kaldir    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping(ENDPOINT_REGISTER)
    public ResponseEntity<RegisterResponseDTO> register (@Valid @RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.ok(authService.doRegister(dto));
    }

    @PostMapping(ENDPOINT_LOGIN)
    public  ResponseEntity<LoginResponseDTO> login (@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.doLogin(dto)); }

///FIX: authenticationManager uzerinden calismasi icin parametrik olarak gelmesi gerekir.
//    @PostMapping(ENDPOINT_LOGIN)
//    public  ResponseEntity<LoginResponseDTO> login (@Valid @RequestBody LoginRequestDTO dto) {
//        return ResponseEntity.ok(authService.doLogin(dto,authenticationManager)); }


//    @GetMapping(ENDPOINT_FIND_ALL)
//    public  ResponseEntity <List<Auth>> findAll (@RequestParam String token) {
//        return ResponseEntity.ok(authService.getRoles(token)); }



//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtUtils.generateJwtToken(authentication);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(new JwtResponse(jwt,
//                userDetails.getId(),
//                userDetails.getUsername(),
//                userDetails.getEmail(),
//                roles));
//    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupReques signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        // Create new user's account
//        User user = new User(signUpRequest.getUsername(),
//                signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()));
//
//        Set<String> strRoles = signUpRequest.getRoles();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }
}
