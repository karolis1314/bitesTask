package com.karolis.bite.controller;

import com.karolis.bite.dto.OrdersDto;
import com.karolis.bite.service.serviceImpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping
    public ResponseEntity<List<OrdersDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdersDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/service/{id}")
    public ResponseEntity<List<OrdersDto>> getOrderByServiceId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderByServiceId(id));
    }

    @GetMapping("/msisdn/{id}")
    public ResponseEntity<List<OrdersDto>> getOrderByMsisdnId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderByMsisdnId(id));
    }

    @PostMapping
    public ResponseEntity<OrdersDto> createOrder(@RequestBody @Validated OrdersDto ordersDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveOrder(ordersDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdersDto> updateOrder(@PathVariable Long id, @RequestBody @Validated OrdersDto ordersDto) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrder(id, ordersDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body("Order with id: " + id + " was deleted");
    }
}
