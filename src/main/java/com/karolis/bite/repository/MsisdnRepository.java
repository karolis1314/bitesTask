package com.karolis.bite.repository;

import com.karolis.bite.model.Msisdn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MsisdnRepository extends JpaRepository<Msisdn, Long> {
}
