package com.karolis.bite.controller;

import com.karolis.bite.dto.ServiceForSaleDto;
import com.karolis.bite.service.serviceImpl.ServiceForSaleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/serviceforsale")
public class ServiceForSaleController {

    @Autowired
    private ServiceForSaleImpl serviceForSaleService;

    @GetMapping
    public ResponseEntity<List<ServiceForSaleDto>> getAllServicesForSale() {
        return ResponseEntity.ok(serviceForSaleService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceForSaleDto> getServiceForSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceForSaleService.getServiceById(id));
    }

    @PostMapping
    public ResponseEntity<ServiceForSaleDto> createServiceForSale(@RequestBody @Validated ServiceForSaleDto serviceForSaleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceForSaleService.saveService(serviceForSaleDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceForSaleDto> updateServiceForSale(@PathVariable Long id, @RequestBody @Validated ServiceForSaleDto serviceForSaleDto) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceForSaleService.updateService(id, serviceForSaleDto));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ServiceForSaleDto> updateServiceForSaleStatus(@PathVariable Long id, @RequestBody @Validated ServiceForSaleDto serviceForSaleDto) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceForSaleService.updateServiceStatus(id, serviceForSaleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServiceForSale(@PathVariable Long id) {
        return ResponseEntity.ok().body("Service with id: " + id + " was deleted");
    }

}
