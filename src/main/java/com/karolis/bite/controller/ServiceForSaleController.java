package com.karolis.bite.controller;

import com.karolis.bite.dto.ServiceForSaleDto;
import com.karolis.bite.service.serviceImpl.ServiceForSaleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/serviceforsale")
public class ServiceForSaleController {

    @Autowired
    private ServiceForSaleImpl serviceForSaleService;

    @GetMapping
    public List<ServiceForSaleDto> getAllServicesForSale() {
        return serviceForSaleService.getAllServices();
    }

    @GetMapping("/{id}")
    public ServiceForSaleDto getServiceForSaleById(@PathVariable Long id) {
        return serviceForSaleService.getServiceById(id);
    }

    @PostMapping
    public ServiceForSaleDto createServiceForSale(@RequestBody @Validated ServiceForSaleDto serviceForSaleDto) {
        return serviceForSaleService.saveService(serviceForSaleDto);
    }

    @PutMapping("/{id}")
    public ServiceForSaleDto updateServiceForSale(@PathVariable Long id, @RequestBody @Validated ServiceForSaleDto serviceForSaleDto) {
        return serviceForSaleService.updateService(id, serviceForSaleDto);
    }

    @DeleteMapping("/{id}")
    public void deleteServiceForSale(@PathVariable Long id) {
        serviceForSaleService.deleteService(id);
    }

}
