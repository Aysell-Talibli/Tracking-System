package org.practice.userservice.repository;

import org.practice.userservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);
}
