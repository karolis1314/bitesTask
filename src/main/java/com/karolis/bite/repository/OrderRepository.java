package com.karolis.bite.repository;

import com.karolis.bite.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    Orders findOrdersById(Long id);
}
