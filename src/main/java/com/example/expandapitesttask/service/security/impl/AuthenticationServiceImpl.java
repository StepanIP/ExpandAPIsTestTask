package com.example.expandapitesttask.service.security.impl;

import com.example.expandapitesttask.dto.request.UserRequest;
import com.example.expandapitesttask.dto.response.JwtAuthenticationResponse;
import com.example.expandapitesttask.models.User;
import com.example.expandapitesttask.repository.UserRepository;
import com.example.expandapitesttask.service.security.AuthenticationService;
import com.example.expandapitesttask.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(UserRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User account = User.builder()
                .email(request.getUsername())
                .password(encodedPassword)
                .build();
        userRepository.save(account);
        var jwt = jwtService.generateToken(account);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(UserRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByEmail(request.getUsername());
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}