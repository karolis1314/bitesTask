package com.karolis.bite.controller;

import com.karolis.bite.dto.AccountDto;
import com.karolis.bite.service.serviceImpl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public AccountDto getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/msisdn/{id}")
    public AccountDto getAccountByMsisdnId(@PathVariable Long id) {
        return accountService.getAccountByMsisdnId(id);
    }

    @GetMapping("/customer/{id}")
    public AccountDto getAccountByCustomerId(@PathVariable Long id) {
        return accountService.getAccountByCustomerId(id);
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody @Validated AccountDto accountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.saveAccount(accountDto));
    }

    @PutMapping("/{id}")
    public AccountDto updateAccount(@PathVariable Long id, @RequestBody @Validated AccountDto accountDto) {
        return accountService.updateAccount(id, accountDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
