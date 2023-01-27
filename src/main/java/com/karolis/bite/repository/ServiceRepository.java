package com.karolis.bite.repository;

import com.karolis.bite.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    Service findByServiceName(String serviceName);
}
