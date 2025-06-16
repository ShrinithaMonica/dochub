package com.shrinitha.dochub.service;

import com.shrinitha.dochub.dto.LoginRequest;
import com.shrinitha.dochub.dto.LoginResponse;
import com.shrinitha.dochub.dto.RegisterRequest;
import com.shrinitha.dochub.model.Role;
import com.shrinitha.dochub.model.User;
import com.shrinitha.dochub.repository.RoleRepository;
import com.shrinitha.dochub.repository.UserRepository;
import com.shrinitha.dochub.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public void register(RegisterRequest request) {
        Set<Role> roles = request.getRoles().stream()
                .map(role -> roleRepo.findByName(role)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role)))
                .collect(Collectors.toSet());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);
        userRepo.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        String token = jwtUtil.generateToken(request.getUsername());
        return new LoginResponse(token);
    }
}
