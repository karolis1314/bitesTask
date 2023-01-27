package com.karolis.bite.service;

import com.karolis.bite.dto.AccountDto;

public interface AccountService {

    void saveAccount(AccountDto account);

    void deleteAccount(Long id);

    AccountDto getAccountById(Long id);

    AccountDto getAccountByMsisdn(String msisdn);

    AccountDto getAccountByMsisdnId(Long msisdnId);

    AccountDto getAccountByCustomerId(Long customerId);
}
