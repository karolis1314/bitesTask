package com.karolis.bite.controller;

import com.karolis.bite.dto.MsisdnDto;
import com.karolis.bite.service.serviceImpl.MsisdnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/msisdn")
public class MsisdnController {

    @Autowired
    private MsisdnServiceImpl msisdnService;

    @GetMapping
    public List<MsisdnDto> getAllMsisdns() {
        return msisdnService.getAllMsisdns();
    }

    @GetMapping("/{id}")
    public void getMsisdnById(@PathVariable Long id) {
        msisdnService.getMsisdnById(id);
    }

    @GetMapping("/account/{id}")
    public List<MsisdnDto> getAllMsisdnsByAccountId(@PathVariable Long id) {
        return msisdnService.getAllMsisdnsByAccountId(id);
    }

    @PostMapping
    public void createMsisdn(@RequestBody MsisdnDto msisdnDto) {
        msisdnService.saveMsisdn(msisdnDto);
    }

    @PutMapping("/{id}")
    public void updateMsisdn(@PathVariable Long id, @RequestBody @Validated MsisdnDto msisdnDto) {
        msisdnService.updateMsisdn(id, msisdnDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMsisdn(@PathVariable Long id) {
        msisdnService.deleteMsisdn(id);
    }


}
