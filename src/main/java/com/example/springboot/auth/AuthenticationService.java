package com.example.springboot.auth;


import com.example.springboot.user.User;
import com.example.springboot.user.UserRepository;
import com.example.springboot.user.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// generates a constructor with 1 parameter for each field that requires special handling  (else the constructor is empty and the userRepository is null which shows error)
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {
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

    public AuthenticationResponse login(AuthenticationRequest request) {
        return null;
    }
}
