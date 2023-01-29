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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.karolis.bite.Constants.AccountExceptionConstants.*;
import static com.karolis.bite.Constants.CommonMethods.formatErrorMessageForConstantMessage;
import static com.karolis.bite.Constants.GeneralErrorMessages.*;

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
            throw new NotFoundException(formatErrorMessageForConstantMessage(ACCOUNT_NOT_FOUND_BY_CUSTOMER_ID, account.getCustomerId()));
        } catch (org.hibernate.PropertyValueException e) {
            throw new PropertyValueException(PROPERTY_VALUE_ERROR);
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public void deleteAccount(Long id) {
        try {
            accountRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(ACCOUNT_NOT_FOUND, id));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
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
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public AccountDto getAccountById(Long id) {
        try {
            return modelMapper.map(accountRepository.findById(id)
                    .orElseThrow(() -> new DataIntegrityViolationException(formatErrorMessageForConstantMessage(ACCOUNT_NOT_FOUND, id))), AccountDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(ACCOUNT_NOT_FOUND, id));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
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
                    .orElseThrow(() -> new DataIntegrityViolationException(formatErrorMessageForConstantMessage(ACCOUNT_NOT_FOUND_BY_MSISDN_ID, id))), AccountDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(ACCOUNT_NOT_FOUND_BY_MSISDN_ID, id));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }


    @Transactional
    @Override
    public List<AccountDto> getAccountByCustomerId(Long customerId) {
        try {
            return accountRepository.findAll().stream()
                    .filter(acc -> acc.getCustomer().getId().equals(customerId))
                    .map(acc -> modelMapper.map(acc, AccountDto.class))
                    .collect(Collectors.toList());
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(ACCOUNT_NOT_FOUND_BY_CUSTOMER_ID, customerId));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
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
           throw new NotFoundException(formatErrorMessageForConstantMessage(ACCOUNT_NOT_FOUND, id));
       } catch (org.hibernate.PropertyValueException e) {
              throw new PropertyValueException(PROPERTY_VALUE_ERROR);
       }catch (Exception e) {
           throw new ServerErrorException(SERVER_ERROR);
       }
    }

    @Transactional
    @Override
    public Account findAccountById(Long accountId) {
        try {
            return accountRepository.findById(accountId).orElseThrow(() -> new DataIntegrityViolationException(formatErrorMessageForConstantMessage(ACCOUNT_NOT_FOUND, accountId)));
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(ACCOUNT_NOT_FOUND, accountId));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }
}
