package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.CustomerDto;
import com.karolis.bite.model.Customer;
import com.karolis.bite.repository.CustomerRepository;
import com.karolis.bite.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Bean
    private ModelMapper modelMapper() {
        return new ModelMapper();
    }
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Transactional
    @Override
    public CustomerDto saveCustomer(CustomerDto customer) {
       return modelMapper()
               .map(customerRepository.save(modelMapper()
                       .map(customer, Customer.class)), CustomerDto.class);
    }

    @Transactional
    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public CustomerDto getCustomerById(Long id) {
        return modelMapper().map(customerRepository.findById(id), CustomerDto.class);
    }

    @Transactional
    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customer -> modelMapper().map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Customer not found with id: " + customerDto.getId()));
        modelMapper().map(customerDto, customer);
        Customer updatedCustomer = customerRepository.save(customer);
        return modelMapper().map(updatedCustomer, CustomerDto.class);
    }

    @Transactional
    @Override
    public CustomerDto getCustomerByEmail(String email) {
        return modelMapper().map(customerRepository.findByEmail(email), CustomerDto.class);
    }

    @Transactional
    @Override
    public CustomerDto getCustomerByPersonalCode(String personalCode) {
        return modelMapper().map(customerRepository.findByPersonalCode(personalCode), CustomerDto.class);
    }
}
