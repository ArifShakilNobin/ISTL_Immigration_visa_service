package istl.immigrationvisa.controller;

import istl.immigrationvisa.domain.entity.Role;
import istl.immigrationvisa.domain.entity.User;
import istl.immigrationvisa.domain.request.LoginRequest;
import istl.immigrationvisa.domain.request.SignupRequest;
import istl.immigrationvisa.domain.response.ApiResponse;
import istl.immigrationvisa.domain.response.JwtResponse;
import istl.immigrationvisa.repositories.RoleRepository;
import istl.immigrationvisa.repositories.UserRepository;
import istl.immigrationvisa.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;



//    @PostMapping("/login")
//    public String authenticateUser(@RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return jwtTokenProvider.generateToken(authentication);
//    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenProvider.generateToken(authentication);

//            return ResponseEntity.ok(new JwtResponse(jwt));
            return ResponseEntity.ok(new ApiResponse(true, "Login successful", jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

//    @PostMapping("/register")
//    public String registerUser(@RequestBody SignupRequest signupRequest) {
//        if (userRepository.existsByUsername(signupRequest.getUsername())) {
//            throw new RuntimeException("Username is already taken!");
//        }
//
//        User user = new User();
//        user.setUsername(signupRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
//
//        // Set roles
//        Role userRole = roleRepository.findByName("ROLE_USER");
//        user.setRoles(Set.of(userRole));
//
//        userRepository.save(user);
//        return "User registered successfully!";
//    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        // Set roles
        Role userRole = roleRepository.findByName(signupRequest.getRole()).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}

