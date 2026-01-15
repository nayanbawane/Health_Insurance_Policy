package com.example.userservice.service;

import com.example.userservice.exception.UserAlreadyExistsException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.User;
import com.example.userservice.model.enums.UserStatus;
import com.example.userservice.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public User createUser(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "Username already exists: " + user.getUsername());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    @Cacheable(value = "user-by-id", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    @Cacheable(value = "user-by-username", key = "#username")
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with username: " + username));
    }

    @Override
    @Cacheable(value = "users-all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {

        User existingUser = getUserById(id);

        existingUser.setUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setMobile(user.getMobile());
        existingUser.setDateOfBirth(user.getDateOfBirth());
        existingUser.setRole(user.getRole());
        existingUser.setStatus(user.getStatus());

        return userRepository.save(existingUser);
    }

    @Override
    public void deactivateUser(Long id) {
        User user = getUserById(id);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    @Override
    public User validateUser(String username, String rawPassword) {
        User user = getUserByUsername(username);

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException("User is not active");
        }

        return user;
    }
}
