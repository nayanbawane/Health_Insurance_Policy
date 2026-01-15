package com.example.auth_service.client;

import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "UserService")
public interface UserClient {

    @GetMapping("/users/username/{username}")
    UserResponse getByUsername(@PathVariable String username);

    @PostMapping("/users")
    UserResponse createUser(@RequestBody RegisterRequest request);
}
