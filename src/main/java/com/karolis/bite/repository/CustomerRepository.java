package com.karolis.bite.repository;

import com.karolis.bite.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByPersonalCode(String personalCode);

    Customer findByEmail(String email);
}
