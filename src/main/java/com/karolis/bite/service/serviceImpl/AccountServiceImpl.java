package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.AccountDto;
import com.karolis.bite.model.Account;
import com.karolis.bite.repository.AccountRepository;
import com.karolis.bite.repository.MsisdnRepository;
import com.karolis.bite.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final ModelMapper modelMapper;

    private AccountRepository accountRepository;

    private MsisdnRepository msisdnRepository;

    @Autowired
    public AccountServiceImpl(ModelMapper modelMapper, AccountRepository accountRepository, MsisdnRepository msisdnRepository) {
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
        this.msisdnRepository = msisdnRepository;
    }
    @Transactional
    @Override
    public AccountDto saveAccount(AccountDto account) {
        return modelMapper
                .map(accountRepository.save(modelMapper
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
                .map(account -> modelMapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccountById(Long id) {
        return modelMapper.map(accountRepository.findById(id), AccountDto.class);
    }

    @Transactional
    @Override
    public AccountDto getAccountByMsisdnId(Long id) {
        Account account =  accountRepository.findAll().stream()
                .filter(acc -> acc.getMsisdns().stream()
                        .anyMatch(m -> m.getId().equals(id)))
                .findFirst()
                .orElse(null);
        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public AccountDto getAccountByCustomerId(Long customerId) {
        return null;
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        return null;
    }

    @Override
    public Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }
}
