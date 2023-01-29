package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.AccountDto;
import com.karolis.bite.exceptions.ConflictException;
import com.karolis.bite.exceptions.NotFoundException;
import com.karolis.bite.exceptions.PropertyValueException;
import com.karolis.bite.exceptions.ServerErrorException;
import com.karolis.bite.model.Account;
import com.karolis.bite.model.Customer;
import com.karolis.bite.repository.AccountRepository;
import com.karolis.bite.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final ModelMapper modelMapper;

    private final AccountRepository accountRepository;

    private final CustomerServiceImpl customerService;

    @Autowired
    public AccountServiceImpl(ModelMapper modelMapper, AccountRepository accountRepository, CustomerServiceImpl customerService) {
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }

    @Transactional
    @Override
    public AccountDto saveAccount(AccountDto account) throws ConflictException {
        try {
            Customer customer = modelMapper.map(customerService.getCustomerById(account.getCustomerId()), Customer.class);
            customer.setAccounts(List.of(modelMapper.map(account, Account.class)));
            return modelMapper.map(accountRepository.save(modelMapper.map(account, Account.class)), AccountDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("Customer with id: " + account.getCustomerId() + " does not exist");
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Customer with id: " + account.getCustomerId() + " does not exist");
        } catch (org.hibernate.PropertyValueException e) {
            throw new PropertyValueException("You are missing parameters, make sure you add all required parameters.");
        } catch (Exception e) {
            throw new ServerErrorException("Something went wrong on our end, please try again later.");
        }
    }

    @Transactional
    @Override
    public void deleteAccount(Long id) {
        try {
            accountRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("Account with id: " + id + " does not exist");
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Account with id: " + id + " does not exist");
        } catch (Exception e) {
            throw new ServerErrorException("Something went wrong on our end, please try again later.");
        }
    }

    @Transactional
    @Override
    public List<AccountDto> getAllAccounts() {
        try {
            return accountRepository
                    .findAll()
                    .stream()
                    .map(account -> modelMapper.map(account, AccountDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServerErrorException("Something went wrong on our end, please try again later.");
        }
    }

    @Transactional
    @Override
    public AccountDto getAccountById(Long id) {
        try {
            return modelMapper.map(accountRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException("Account not found")), AccountDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("Account with id: " + id + " does not exist");
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Account with id: " + id + " does not exist");
        } catch (Exception e) {
            throw new ServerErrorException("Something went wrong on our end, please try again later.");
        }
    }

    @Transactional
    @Override
    public AccountDto getAccountByMsisdnId(Long id) {
        try {
            return modelMapper.map(accountRepository.findAll().stream()
                    .filter(acc -> acc.getMsisdns().stream()
                            .anyMatch(m -> m.getId().equals(id)))
                    .findFirst()
                    .orElseThrow(() -> new DataIntegrityViolationException("Not Found")), AccountDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("Account with msisdnId: " + id + " does not exist");
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Account with msisdnId: " + id + " does not exist");
        } catch (Exception e) {
            throw new ServerErrorException("Something went wrong on our end, please try again later.");
        }
    }


    @Transactional
    @Override
    public AccountDto getAccountByCustomerId(Long customerId) {
        try {
            return modelMapper.map(accountRepository.findAll().stream()
                    .filter(cus -> cus.getCustomer().getId().equals(customerId))
                    .findFirst()
                    .orElseThrow(() -> new DataIntegrityViolationException("Not Found")), AccountDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("Account with customerId: " + customerId + " does not exist");
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Account with customerId: " + customerId + " does not exist");
        } catch (Exception e) {
            throw new ServerErrorException("Something went wrong on our end, please try again later.");
        }
    }

    @Transactional
    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
       try {
           Account account = accountRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException("Account not found"));
           BeanUtils.copyProperties(accountDto, account, "id", "msisdns");
           return modelMapper.map(accountRepository.save(account), AccountDto.class);
       } catch (DataIntegrityViolationException e) {
           throw new NotFoundException("Account with id: " + id + " does not exist");
       } catch (EmptyResultDataAccessException e) {
           throw new NotFoundException("Account with id: " + id + " does not exist");
       } catch (org.hibernate.PropertyValueException e) {
              throw new PropertyValueException("Account with id: " + id + " already exists.");
       }catch (Exception e) {
           throw new ServerErrorException("Something went wrong on our end, please try again later.");
       }
    }

    @Transactional
    @Override
    public Account findAccountById(Long accountId) {
        try {
            return accountRepository.findById(accountId).orElseThrow(() -> new DataIntegrityViolationException("Account not found"));
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("Account with id: " + accountId + " does not exist");
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Account with id: " + accountId + " does not exist");
        } catch (Exception e) {
            throw new ServerErrorException("Something went wrong on our end, please try again later.");
        }
    }
}
