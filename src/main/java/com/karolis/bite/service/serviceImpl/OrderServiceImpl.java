package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.OrdersDto;
import com.karolis.bite.exceptions.NotFoundException;
import com.karolis.bite.exceptions.PropertyValueException;
import com.karolis.bite.exceptions.ServerErrorException;
import com.karolis.bite.model.Msisdn;
import com.karolis.bite.model.Orders;
import com.karolis.bite.repository.OrderRepository;
import com.karolis.bite.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.karolis.bite.Constants.CommonMethods.formatErrorMessageForConstantMessage;
import static com.karolis.bite.Constants.GeneralErrorMessages.*;
import static com.karolis.bite.Constants.OrderExceptionConstants.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;

    private final OrderRepository orderRepository;

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
       } catch (DataIntegrityViolationException e) {
           throw new NotFoundException(formatErrorMessageForConstantMessage(ORDER_NOT_FOUND, order.getId()));
       } catch (org.hibernate.PropertyValueException e) {
           throw new PropertyValueException(PROPERTY_VALUE_ERROR);
       } catch (Exception e) {
           throw new ServerErrorException(SERVER_ERROR);
       }
    }

    @Transactional
    @Override
    public void deleteOrder(Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(ORDER_NOT_FOUND, id));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public OrdersDto getOrderById(Long id) {
        try {
            return modelMapper.map(orderRepository.findOrdersById(id), OrdersDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(ORDER_NOT_FOUND, id));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public List<OrdersDto> getOrderByServiceId(Long serviceId) {
        try {
            return orderRepository.findAll()
                    .stream()
                    .filter(order -> order.getServiceId().equals(serviceId))
                    .map(order -> modelMapper.map(order, OrdersDto.class))
                    .collect(Collectors.toList());
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(ORDER_NOT_FOUND_BY_SERVICE_ID, serviceId));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public List<OrdersDto> getOrderByMsisdnId(Long msisdnId) {
        try {
            return orderRepository.findAll()
                    .stream()
                    .filter(order -> order.getMsisdn().getId().equals(msisdnId))
                    .map(order -> modelMapper.map(order, OrdersDto.class))
                    .collect(Collectors.toList());
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(ORDER_NOT_FOUND_BY_MSISDN_ID, msisdnId));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
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
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public OrdersDto updateOrder(Long id, OrdersDto ordersDto) {
        try {
            Orders orders = orderRepository.findById(id).orElseThrow( ()-> new DataIntegrityViolationException(formatErrorMessageForConstantMessage(ORDER_NOT_FOUND, id)));
            BeanUtils.copyProperties(ordersDto, orders, "id", "name", "serviceId", "msisdnId", "activeFrom", "activeTo");
            Msisdn msisdn = orders.getMsisdn();
            orders.setActiveTo(msisdn.getActiveTo());
            return modelMapper.map(orderRepository.save(orders), OrdersDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(ORDER_NOT_FOUND, id));
        } catch (org.hibernate.PropertyValueException e) {
            throw new PropertyValueException(PROPERTY_VALUE_ERROR);
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }
}
