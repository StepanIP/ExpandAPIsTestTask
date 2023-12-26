package com.example.expandapitesttask.service;

import com.example.expandapitesttask.ExpandApiTestTaskApplication;
import com.example.expandapitesttask.dto.request.UserRequest;
import com.example.expandapitesttask.dto.response.JwtAuthenticationResponse;
import com.example.expandapitesttask.models.User;
import com.example.expandapitesttask.repository.UserRepository;
import com.example.expandapitesttask.service.security.JwtService;
import com.example.expandapitesttask.service.security.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest(classes= ExpandApiTestTaskApplication.class)
public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    public void testSignup() {
        UserRequest signUpRequest = new UserRequest("johndoe@example.com", "password");

        User savedUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        when(jwtService.generateToken(any(User.class))).thenReturn("generated-token");

        authenticationService.signup(signUpRequest);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testSignin() {
        UserRequest signInRequest = new UserRequest("johndoe@example.com", "password");

        User user = User.builder()
                .email("johndoe@example.com")
                .password("encoded-password")
                .build();

        when(userRepository.findByEmail("johndoe@example.com")).thenReturn(user);

        authenticationService.signin(signInRequest);

        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken("johndoe@example.com", "password"));
        verify(jwtService, times(1)).generateToken(any(UserDetails.class));
    }
}
