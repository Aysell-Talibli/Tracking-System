package org.practice.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.practice.userservice.dto.CustomerProfileDto;
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

import java.util.Collection;
import java.util.List;
import java.util.Optional;


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
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String token = jwtService.generateToken(loginRequestDto.getEmail(),authorities);
        LoginResponseDto authResponseDto = new LoginResponseDto(loginRequestDto.getEmail(), token);
        return authResponseDto;

    }
    @Override
    public CustomerProfileDto getProfile(String authHeader){
       if(authHeader == null || !authHeader.startsWith("Bearer ")){
           throw new RuntimeException("Missing or invalid Authorization Header");
       }

       String token=authHeader.substring(7);
       String email= jwtService.extractEmail(token);
        Customer customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

       return new CustomerProfileDto(
               customer.getFirstName(),
               customer.getLastName(),
               customer.getEmail(),
               customer.getPhoneNumber(),
               customer.getRole()
       );
    }

}
