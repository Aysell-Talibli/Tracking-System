package org.practice.userservice.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers", schema = "user_service")
public class Customer {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;


    @Column(name = "password")
    private String password;

    @NotNull
    @Column(unique = true, name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(unique = true, name = "email")
    private String email;

    @NotNull
    @Column(name = "role")
    private String role;

    private String address;


}
