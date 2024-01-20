package com.example.springboot.auth;

import com.example.springboot.user.User;
import com.example.springboot.user.UserRepository;
import com.example.springboot.user.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
// generates a constructor with 1 parameter for each field that requires special handling  (else the constructor is empty and the userRepository is null which shows error)
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        boolean isPresent = userRepository.findByEmail(request.getEmail()).isPresent();
        if (isPresent)
            throw new IllegalStateException("email already taken");

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        var token = jwtService.createToken(user.getEmail());

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()
                    ));
        } catch (Exception e) {
            System.out.println("AuthenticationService: login: catch: " + e.getMessage());
            throw new BadCredentialsException("invalid credentials");
        }

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var token = jwtService.createToken(user.getEmail()); // getUsername() returns email
        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }
}
