package com.karolis.bite.service;

import com.karolis.bite.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto saveCustomer(CustomerDto customer);

    void deleteCustomer(Long id);

    CustomerDto getCustomerById(Long id);

    List<CustomerDto> getAllCustomers();

    CustomerDto updateCustomer(Long id, CustomerDto customerDto);

    CustomerDto getCustomerByEmail(String email);

    CustomerDto getCustomerByPersonalCode(String personalCode);
}
