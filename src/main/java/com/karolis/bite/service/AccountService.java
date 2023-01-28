package com.karolis.bite.service;

import com.karolis.bite.dto.AccountDto;
import com.karolis.bite.model.Account;

import java.util.List;

public interface AccountService {

    AccountDto saveAccount(AccountDto account);

    void deleteAccount(Long id);

    List<AccountDto> getAllAccounts();

    AccountDto getAccountById(Long id);

    AccountDto getAccountByMsisdnId(Long id);

    AccountDto getAccountByCustomerId(Long customerId);

    AccountDto updateAccount(Long id, AccountDto accountDto);

    Account findAccountById(Long accountId);
}
