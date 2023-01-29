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
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping("/msisdn/{id}")
    public ResponseEntity<AccountDto> getAccountByMsisdnId(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountByMsisdnId(id));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<AccountDto> getAccountByCustomerId(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountByCustomerId(id));
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody @Validated AccountDto accountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.saveAccount(accountDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody @Validated AccountDto accountDto) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.updateAccount(id, accountDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.OK).body("Account with id: " + id + " was deleted");
    }
}
