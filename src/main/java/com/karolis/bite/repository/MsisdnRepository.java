package com.karolis.bite.repository;

import com.karolis.bite.model.Msisdn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MsisdnRepository extends JpaRepository<Msisdn, Long> {
    List<Msisdn> findAllByAccountId(Long accountId);

    Msisdn findByOrderId(Long orderId);
}
