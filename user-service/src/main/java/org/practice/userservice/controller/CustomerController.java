package org.practice.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.practice.userservice.dto.LoginRequestDto;
import org.practice.userservice.dto.LoginResponseDto;
import org.practice.userservice.dto.CustomerDto;
import org.practice.userservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/profile")
    public String profile() {
        return "User profile";
    }

}
