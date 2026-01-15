package com.example.userservice.model.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String role;
    private String status;

}
