package com.karolis.bite.service;

import com.karolis.bite.dto.ServiceForSaleDto;

import java.util.List;

public interface ServiceForSaleService {

    ServiceForSaleDto saveService(ServiceForSaleDto service);

    void deleteService(Long id);

    ServiceForSaleDto getServiceById(Long id);

    List<ServiceForSaleDto> getAllServices();

    ServiceForSaleDto updateService(Long id, ServiceForSaleDto serviceForSaleDto);

    ServiceForSaleDto updateServiceStatus(Long id, ServiceForSaleDto serviceForSaleDto);

}
