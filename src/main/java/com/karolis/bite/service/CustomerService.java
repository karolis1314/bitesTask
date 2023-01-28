package com.karolis.bite.service;

import com.karolis.bite.dto.CustomerDto;

public interface CustomerService {

    CustomerDto saveCustomer(CustomerDto customer);

    void deleteCustomer(Long id);

    CustomerDto getCustomerById(Long id);

    CustomerDto getCustomerByEmail(String email);

    CustomerDto getCustomerByPersonalCode(String personalCode);

}
