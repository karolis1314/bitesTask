package com.karolis.bite.repository;

import com.karolis.bite.model.ServiceForSale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceForSale, Long> {

    ServiceForSale findByServiceName(String serviceName);
}
