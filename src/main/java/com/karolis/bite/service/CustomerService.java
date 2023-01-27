package com.karolis.bite.service;

import com.karolis.bite.dto.CustomerDto;

public interface CustomerService {

    void saveCustomer(CustomerDto customer);

    void deleteCustomer(CustomerDto customer);

    CustomerDto getCustomerById(Long id);

    CustomerDto getCustomerByEmail(String email);

    CustomerDto getCustomerByPersonalCode(String personalCode);

}
