package com.karolis.bite.service;

import com.karolis.bite.dto.OrdersDto;

import java.util.List;

public interface OrderService {

    OrdersDto saveOrder(OrdersDto order);

    void deleteOrder(Long id);

    OrdersDto getOrderById(Long id);

    List<OrdersDto> getOrderByServiceId(Long serviceId);

    List<OrdersDto> getOrderByMsisdnId(Long msisdnId);

    List<OrdersDto> getAllOrders();

    OrdersDto updateOrder(Long id, OrdersDto ordersDto);
}
