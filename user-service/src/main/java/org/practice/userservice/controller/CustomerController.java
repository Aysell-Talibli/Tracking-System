package org.practice.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.practice.userservice.dto.LoginRequestDto;
import org.practice.userservice.dto.LoginResponseDto;
import org.practice.userservice.dto.CustomerDto;
import org.practice.userservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("auth/register")
    public ResponseEntity<String> register(@RequestBody CustomerDto customerDto) {
        customerService.saveCustomer(customerDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("auth/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = customerService.login(loginRequestDto);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);

    }

    @GetMapping("/users/me")
    @PreAuthorize("hasRole('USER')")
    public String profile() {
        return "User profile";
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "Admin profile";
    }

}
