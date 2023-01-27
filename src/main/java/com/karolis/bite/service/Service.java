package com.karolis.bite.service;

import com.karolis.bite.dto.ServiceDto;

public interface Service {

    void saveService(ServiceDto service);

    void deleteService(Long id);

    ServiceDto getServiceById(Long id);
}
