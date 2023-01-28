package com.karolis.bite.service;

import com.karolis.bite.dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto saveAccount(AccountDto account);

    void deleteAccount(Long id);

    List<AccountDto> getAllAccounts();

    AccountDto getAccountById(Long id);

    AccountDto getAccountByMsisdn(Long id);

    AccountDto getAccountByMsisdnId(Long msisdnId);

    AccountDto getAccountByCustomerId(Long customerId);

    AccountDto updateAccount(Long id, AccountDto accountDto);
}
