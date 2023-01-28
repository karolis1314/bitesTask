package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.OrdersDto;
import com.karolis.bite.model.Msisdn;
import com.karolis.bite.model.Orders;
import com.karolis.bite.repository.OrderRepository;
import com.karolis.bite.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;

    private OrderRepository orderRepository;

    private final MsisdnServiceImpl msisdnService;

    @Autowired
    public OrderServiceImpl(ModelMapper modelMapper, OrderRepository orderRepository, MsisdnServiceImpl msisdnService) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.msisdnService = msisdnService;
    }
    @Transactional
    @Override
    public OrdersDto saveOrder(OrdersDto order) {
       try {
           Orders orders = modelMapper.map(order, Orders.class);
           Msisdn msisdn = modelMapper.map(msisdnService.getMsisdnById(order.getMsisdnId()), Msisdn.class);
           orders.setMsisdn(msisdn);
           return modelMapper.map(orderRepository.save(orders), OrdersDto.class);
       } catch (Exception e) {
           throw new ResourceAccessException("Order with id: " + order.getId() + " already exists");
       }
    }

    @Transactional
    @Override
    public void deleteOrder(Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceAccessException("Order with id: " + id + " does not exist");
        }
    }

    @Transactional
    @Override
    public OrdersDto getOrderById(Long id) {
        try {
            return modelMapper.map(orderRepository.findById(id), OrdersDto.class);
        } catch (Exception e) {
            throw new ResourceAccessException("Order with id: " + id + " does not exist");
        }
    }

    @Transactional
    @Override
    public OrdersDto getOrderByServiceId(Long serviceId) {
        try {
            return modelMapper.map(orderRepository.findByServiceId(serviceId), OrdersDto.class);
        } catch (Exception e) {
            throw new ResourceAccessException("Order with service id: " + serviceId + " does not exist");
        }
    }

    @Transactional
    @Override
    public OrdersDto getOrderByMsisdnId(Long msisdnId) {
        try {
            return modelMapper.map(orderRepository.findByMsisdnId(msisdnId), OrdersDto.class);
        } catch (Exception e) {
            throw new ResourceAccessException("Order with msisdn id: " + msisdnId + " does not exist");
        }
    }

    @Transactional
    @Override
    public List<OrdersDto> getAllOrders() {
        try {
            return orderRepository
                    .findAll()
                    .stream()
                    .map(order -> modelMapper.map(order, OrdersDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResourceAccessException("No orders found");
        }
    }

    @Transactional
    @Override
    public OrdersDto updateOrder(Long id, OrdersDto ordersDto) {
        try {
            Orders orders = orderRepository.findById(id).get();
            BeanUtils.copyProperties(ordersDto, orders, "id", "name", "serviceId", "msisdnId", "activeFrom");
            return modelMapper.map(orderRepository.save(orders), OrdersDto.class);
        } catch (Exception e) {
            throw new ResourceAccessException("Order with id: " + id + " does not exist");
        }
    }
}
