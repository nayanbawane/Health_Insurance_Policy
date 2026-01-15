package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.model.dto.LoginRequest;
import com.example.userservice.model.enums.UserStatus;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/welcome")
    public String greeting() {
        return  "User Controller!";
    }


    // 🔹 Called by Auth Service during registration
    @PostMapping("/save")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // 🔹 Used by other services
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // 🔹 Used by Gateway / Auth Service
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    // 🔹 Admin use
    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>>
    getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 🔹 Update profile
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // 🔹 Soft delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok("User deactivated successfully");
    }


    @PostMapping("/users/validate")
    public User validateUser(@RequestBody LoginRequest request) {
        return userService.validateUser(
                request.getUsername(),
                request.getPassword()
        );
    }

}
