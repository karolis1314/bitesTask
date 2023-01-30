package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.CustomerDto;
import com.karolis.bite.exceptions.DublicateException;
import com.karolis.bite.exceptions.NotFoundException;
import com.karolis.bite.exceptions.PropertyValueException;
import com.karolis.bite.exceptions.ServerErrorException;
import com.karolis.bite.model.Customer;
import com.karolis.bite.repository.CustomerRepository;
import com.karolis.bite.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.karolis.bite.Constants.CommonMethods.formatErrorMessageForConstantMessage;
import static com.karolis.bite.Constants.CustomerExceptionConstants.*;
import static com.karolis.bite.Constants.GeneralErrorMessages.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper modelMapper;

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(ModelMapper modelMapper, CustomerRepository customerRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }
    @Transactional
    @Override
    public CustomerDto saveCustomer(CustomerDto customer) {
        try {
            Customer customerToSave = modelMapper.map(customer, Customer.class);
            Customer toCheck = customerRepository.findByEmail(customer.getEmail());
            if(toCheck != null && !toCheck.getId().equals(customer.getId())){
                throw new DublicateException(formatErrorMessageForConstantMessage(CUSTOMER_EMAIL_ALREADY_EXISTS, customer.getEmail()));
            }
            return modelMapper.map(customerRepository.save(customerToSave), CustomerDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(CUSTOMER_NOT_FOUND, customer.getId()));
        } catch(DublicateException e){
            throw new DublicateException(formatErrorMessageForConstantMessage(CUSTOMER_EMAIL_ALREADY_EXISTS, customer.getEmail()));
        }catch (org.hibernate.PropertyValueException e) {
            throw new PropertyValueException(PROPERTY_VALUE_ERROR);
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public void deleteCustomer(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(CUSTOMER_NOT_FOUND, id));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public CustomerDto getCustomerById(Long id) {
        try {
            return modelMapper.map(customerRepository.findById(id)
                    .orElseThrow(() -> new DataIntegrityViolationException(formatErrorMessageForConstantMessage(CUSTOMER_NOT_FOUND, id))), CustomerDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(CUSTOMER_NOT_FOUND, id));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public List<CustomerDto> getAllCustomers() {
        try {
            return customerRepository
                    .findAll()
                    .stream()
                    .map(customer -> modelMapper.map(customer, CustomerDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        try {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new DataIntegrityViolationException(formatErrorMessageForConstantMessage(CUSTOMER_NOT_FOUND, id)));
            BeanUtils.copyProperties(customerDto, customer, "id", "personalCode");
            return modelMapper.map(customerRepository.save(customer), CustomerDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(CUSTOMER_NOT_FOUND, id));
        } catch (org.hibernate.PropertyValueException e) {
            throw new PropertyValueException(PROPERTY_VALUE_ERROR);
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public CustomerDto getCustomerByEmail(String email) {
        try {
            return modelMapper.map(customerRepository.findByEmail(email), CustomerDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(CUSTOMER_NOT_FOUND_BY_EMAIL, email));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public CustomerDto getCustomerByPersonalCode(String personalCode) {
        try {
            return modelMapper.map(customerRepository.findByPersonalCode(personalCode), CustomerDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(CUSTOMER_NOT_FOUND_BY_PERSONAL_CODE, personalCode));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }
}
