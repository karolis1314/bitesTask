package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.AccountDto;
import com.karolis.bite.model.Account;
import com.karolis.bite.repository.AccountRepository;
import com.karolis.bite.repository.MsisdnRepository;
import com.karolis.bite.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Bean
    private ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private AccountRepository accountRepository;

    private MsisdnRepository msisdnRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Transactional
    @Override
    public AccountDto saveAccount(AccountDto account) {
        return modelMapper()
                .map(accountRepository.save(modelMapper()
                        .map(account, Account.class)), AccountDto.class);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> modelMapper().map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccountById(Long id) {
        return modelMapper().map(accountRepository.findById(id), AccountDto.class);
    }

    @Override
    public AccountDto getAccountByMsisdn(Long id) {
        return  msisdnRepository.findById(id)
                .map(msisdn -> modelMapper().map(msisdn.getAccount(), AccountDto.class))
                .orElseThrow(() -> new ResourceAccessException("Msisdn not found"));
    }

    @Override
    public AccountDto getAccountByMsisdnId(Long msisdnId) {
        return null;
    }

    @Override
    public AccountDto getAccountByCustomerId(Long customerId) {
        return null;
    }
}
