package org.practice.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.practice.userservice.dto.LoginResponseDto;
import org.practice.userservice.dto.CustomerDto;
import org.practice.userservice.dto.LoginRequestDto;
import org.practice.userservice.model.Customer;
import org.practice.userservice.repository.UserRepository;
import org.practice.userservice.service.CustomerService;
import org.practice.userservice.util.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Override
    public Customer saveCustomer(CustomerDto customerDto) {
        Customer customer= Customer.builder()
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
        Customer customer=userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), customer.getPassword())){
            throw new BadCredentialsException("Wrong credentials");
        }
        String token = jwtUtil.generateToken(customer.getEmail());
        LoginResponseDto authResponseDto=new LoginResponseDto(customer.getEmail(), token);
        return authResponseDto;
    }
}
