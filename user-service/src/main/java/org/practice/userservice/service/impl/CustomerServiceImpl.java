package org.practice.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.practice.userservice.dto.LoginResponseDto;
import org.practice.userservice.dto.CustomerDto;
import org.practice.userservice.dto.LoginRequestDto;
import org.practice.userservice.model.Customer;
import org.practice.userservice.repository.UserRepository;
import org.practice.userservice.service.CustomerService;
import org.practice.userservice.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Customer saveCustomer(CustomerDto customerDto) {
        Customer customer = Customer.builder()
                .firstName(customerDto.getFirst_name())
                .password(passwordEncoder.encode(customerDto.getPassword()))
                .phoneNumber(customerDto.getPhone_number())
                .lastName(customerDto.getLast_name())
                .email(customerDto.getEmail())
                .role(customerDto.getRole())
                .build();
        userRepository.save(customer);
        return customer;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()));
        String token = jwtService.generateToken(loginRequestDto.getEmail());
        LoginResponseDto authResponseDto = new LoginResponseDto(loginRequestDto.getEmail(), token);
        return authResponseDto;

    }
}
