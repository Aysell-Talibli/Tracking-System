package org.practice.userservice.service;

import org.practice.userservice.dto.CustomerProfileDto;
import org.practice.userservice.dto.LoginResponseDto;
import org.practice.userservice.dto.CustomerDto;
import org.practice.userservice.dto.LoginRequestDto;
import org.practice.userservice.model.Customer;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    Customer saveCustomer(CustomerDto customerDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    CustomerProfileDto getProfile(String authHeader);
}