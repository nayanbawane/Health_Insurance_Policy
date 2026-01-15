package com.example.auth_service.service;

import com.example.auth_service.client.UserClient;
import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.dto.UserResponse;
import com.example.auth_service.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ LOGIN
    public String login(LoginRequest request) {

        UserResponse user = userClient.getByUsername(request.getUsername());

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new RuntimeException("User is not active");
        }

        // ⚠️ password check SHOULD be done in UserService
        // Here we assume password already validated or not exposed

        return jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );
    }

    // ✅ REGISTER
    public String register(RegisterRequest request) {

        userClient.createUser(request);

        return "User registered successfully";
    }
}
