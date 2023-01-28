package com.karolis.bite.controller;

import com.karolis.bite.dto.OrdersDto;
import com.karolis.bite.service.serviceImpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping
    public List<OrdersDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrdersDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/service/{id}")
    public OrdersDto getOrderByServiceId(@PathVariable Long serviceId) {
        return orderService.getOrderByServiceId(serviceId);
    }

    @GetMapping("/msisdn/{id}")
    public OrdersDto getOrderByMsisdnId(@PathVariable Long msisdnId) {
        return orderService.getOrderByMsisdnId(msisdnId);
    }

    @PostMapping
    public OrdersDto createOrder(@RequestBody @Validated OrdersDto ordersDto) {
        return orderService.saveOrder(ordersDto);
    }

    @PutMapping("/{id}")
    public OrdersDto updateOrder(@PathVariable Long id, @RequestBody @Validated OrdersDto ordersDto) {
        return orderService.updateOrder(id, ordersDto);
    }
}
