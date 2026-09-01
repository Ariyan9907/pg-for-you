package org.bridgelabz.pgforyou.service;

import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.dto.request.LoginRequestDTO;
import org.bridgelabz.pgforyou.dto.request.RegisterRequestDTO;
import org.bridgelabz.pgforyou.model.User;
import org.bridgelabz.pgforyou.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // Register user
    public void register(RegisterRequestDTO requestDTO) {

        // Check whether email already exists
        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());

        // Encrypt password before storing it
        user.setPassword(
                passwordEncoder.encode(requestDTO.getPassword())
        );

        // Default role
        user.setRole("USER");

        userRepository.save(user);
    }

    // Login user using Spring Security AuthenticationManager
    public String login(LoginRequestDTO requestDTO) {

        // Authenticate email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getEmail(),
                        requestDTO.getPassword()
                )
        );

        // Authentication successful
        // Generate JWT
        return jwtService.generateToken(
                requestDTO.getEmail()
        );
    }
}