package com.shrinitha.dochub.service;

import com.shrinitha.dochub.dto.LoginRequest;
import com.shrinitha.dochub.dto.LoginResponse;
import com.shrinitha.dochub.dto.RegisterRequest;
import com.shrinitha.dochub.model.Role;
import com.shrinitha.dochub.model.User;
import com.shrinitha.dochub.repository.RoleRepository;
import com.shrinitha.dochub.repository.UserRepository;
import com.shrinitha.dochub.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private RoleRepository roleRepo;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setPassword("password123");
        request.setRoles(Set.of("USER"));

        Role userRole = new Role();
        userRole.setName("USER");

        when(roleRepo.findByName("USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPass");

        authService.register(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("john", savedUser.getUsername());
        assertEquals("encodedPass", savedUser.getPassword());
        assertTrue(savedUser.getRoles().contains(userRole));
    }

    @Test
    void shouldThrowExceptionWhenRoleNotFound() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setPassword("password123");
        request.setRoles(Set.of("INVALID_ROLE"));

        when(roleRepo.findByName("INVALID_ROLE")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.register(request));
        assertEquals("Role not found: INVALID_ROLE", ex.getMessage());
    }

    @Test
    void shouldLoginAndReturnToken() {
        LoginRequest request = new LoginRequest();
        request.setUsername("john");
        request.setPassword("password123");

        when(jwtUtil.generateToken("john")).thenReturn("mocked-jwt-token");

        LoginResponse response = authService.login(request);

        verify(authManager).authenticate(
                new UsernamePasswordAuthenticationToken("john", "password123")
        );
        assertEquals("mocked-jwt-token", response.getToken());
    }
}
