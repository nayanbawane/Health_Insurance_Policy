package com.example.userservice.service;


import com.example.userservice.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(Long id);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    User updateUser(Long id, User user);

    void deactivateUser(Long id);
}
