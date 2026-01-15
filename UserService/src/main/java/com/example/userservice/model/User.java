package com.example.userservice.model;


import com.example.userservice.model.enums.UserRole;
import com.example.userservice.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;


    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;


    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Mobile number must be valid 10-digit Indian number"
    )
    private String mobile;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = UserStatus.ACTIVE;
        if (role == null) role = UserRole.CUSTOMER;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @JsonIgnore
    @Column(nullable = false)
    private String password;


}
