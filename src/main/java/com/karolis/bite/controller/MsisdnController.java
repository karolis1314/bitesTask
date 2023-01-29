package com.karolis.bite.controller;

import com.karolis.bite.dto.MsisdnDto;
import com.karolis.bite.service.serviceImpl.MsisdnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/msisdn")
public class MsisdnController {

    @Autowired
    private MsisdnServiceImpl msisdnService;

    @GetMapping
    public ResponseEntity<List<MsisdnDto>> getAllMsisdns() {
        return ResponseEntity.ok(msisdnService.getAllMsisdns());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MsisdnDto> getMsisdnById(@PathVariable Long id) {
        return ResponseEntity.ok(msisdnService.getMsisdnById(id));
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<List<MsisdnDto>> getAllMsisdnsByAccountId(@PathVariable Long id) {
        return ResponseEntity.ok(msisdnService.getAllMsisdnsByAccountId(id));
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<MsisdnDto> getMsisdnsByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(msisdnService.getMsisdnByOrderId(id));
    }

    @PostMapping
    public ResponseEntity<MsisdnDto> createMsisdn(@RequestBody MsisdnDto msisdnDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(msisdnService.saveMsisdn(msisdnDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MsisdnDto> updateMsisdn(@PathVariable Long id, @RequestBody @Validated MsisdnDto msisdnDto) {
        return ResponseEntity.status(HttpStatus.OK).body(msisdnService.updateMsisdnExtendTwoYears(id, msisdnDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMsisdn(@PathVariable Long id) {
        msisdnService.deleteMsisdn(id);
        return ResponseEntity.status(HttpStatus.OK).body("Msisdn with id: " + id + " was deleted");
    }


}
