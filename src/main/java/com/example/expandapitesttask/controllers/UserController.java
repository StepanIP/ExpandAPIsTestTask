package com.example.expandapitesttask.controllers;

import com.example.expandapitesttask.dto.request.UserRequest;
import com.example.expandapitesttask.dto.response.JwtAuthenticationResponse;
import com.example.expandapitesttask.service.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user") //usually it is /api/v1/ but I did as in the instruction
public class UserController {

    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> createUser(@RequestBody UserRequest request) {
        authenticationService.signup(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }


}
