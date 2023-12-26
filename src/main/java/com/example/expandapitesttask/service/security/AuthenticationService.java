package com.example.expandapitesttask.service.security;


import com.example.expandapitesttask.dto.request.UserRequest;
import com.example.expandapitesttask.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(UserRequest request);
    JwtAuthenticationResponse signin(UserRequest request);
}