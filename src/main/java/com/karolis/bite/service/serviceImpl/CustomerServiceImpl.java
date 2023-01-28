package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.CustomerDto;
import com.karolis.bite.model.Customer;
import com.karolis.bite.repository.CustomerRepository;
import com.karolis.bite.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class CustomerServiceImpl implements CustomerService {
    @Bean
    private ModelMapper modelMapper() {
        return new ModelMapper();
    }
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    public CustomerDto saveCustomer(CustomerDto customer) {
        customerRepository.save(modelMapper().map(customer, Customer.class));
        return customer;
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return modelMapper().map(customerRepository.findById(id), CustomerDto.class);
    }

    @Override
    public CustomerDto getCustomerByEmail(String email) {
        return modelMapper().map(customerRepository.findByEmail(email), CustomerDto.class);
    }

    @Override
    public CustomerDto getCustomerByPersonalCode(String personalCode) {
        return modelMapper().map(customerRepository.findByPersonalCode(personalCode), CustomerDto.class);
    }
}
