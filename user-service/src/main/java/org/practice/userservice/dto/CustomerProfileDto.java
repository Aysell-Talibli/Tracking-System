package org.practice.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProfileDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;
}
