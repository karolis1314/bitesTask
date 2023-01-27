package com.karolis.bite.service;

import com.karolis.bite.dto.OrdersDto;

public interface OrderService {

    void saveOrder(OrdersDto order);

    void deleteOrder(Long id);

    OrdersDto getOrderById(Long id);

    OrdersDto getOrderByServiceId(Long serviceId);

    OrdersDto getOrderByMsisdnId(Long msisdnId);
}
