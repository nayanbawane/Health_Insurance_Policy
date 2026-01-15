package com.example.userservice.model.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String dateOfBirth;
}
