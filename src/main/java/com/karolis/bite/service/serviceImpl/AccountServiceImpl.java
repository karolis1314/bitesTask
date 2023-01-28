package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.AccountDto;
import com.karolis.bite.model.Account;
import com.karolis.bite.repository.AccountRepository;
import com.karolis.bite.repository.MsisdnRepository;
import com.karolis.bite.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final ModelMapper modelMapper;

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(ModelMapper modelMapper, AccountRepository accountRepository, MsisdnRepository msisdnRepository) {
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
    }
    @Transactional
    @Override
    public AccountDto saveAccount(AccountDto account) {
        return modelMapper.map(accountRepository.save(modelMapper
                        .map(account, Account.class)), AccountDto.class);
    }

    @Transactional
    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> modelMapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AccountDto getAccountById(Long id) {
        return modelMapper.map(accountRepository.findById(id), AccountDto.class);
    }

    @Transactional
    @Override
    public AccountDto getAccountByMsisdnId(Long id) {
        return modelMapper.map(accountRepository.findAll().stream()
                .filter(acc -> acc.getMsisdns().stream()
                        .anyMatch(m -> m.getId().equals(id)))
                .findFirst()
                .orElse(null), AccountDto.class);
    }


    @Transactional
    @Override
    public AccountDto getAccountByCustomerId(Long customerId) {
        return modelMapper.map(accountRepository.findAll().stream()
                .filter(cus -> cus.getCustomer().getId().equals(customerId))
                .findFirst()
                .orElse(null), AccountDto.class);
    }

    @Transactional
    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Account not found"));
        BeanUtils.copyProperties(accountDto, account, "id", "msisdns");
        return modelMapper.map(accountRepository.save(account), AccountDto.class);
    }

    @Transactional
    @Override
    public Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }
}
