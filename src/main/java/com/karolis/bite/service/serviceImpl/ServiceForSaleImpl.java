package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.ServiceForSaleDto;
import com.karolis.bite.model.ServiceForSale;
import com.karolis.bite.repository.ServiceRepository;
import com.karolis.bite.service.ServiceForSaleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceForSaleImpl implements ServiceForSaleService {

    private final ModelMapper modelMapper;

    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceForSaleImpl(ModelMapper modelMapper, ServiceRepository serviceRepository) {
        this.modelMapper = modelMapper;
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    @Override
    public ServiceForSaleDto saveService(ServiceForSaleDto service) {
        ServiceForSale serviceForSale = modelMapper.map(service, ServiceForSale.class);
        return modelMapper.map(serviceRepository.save(serviceForSale), ServiceForSaleDto.class);
    }

    @Transactional
    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ServiceForSaleDto getServiceById(Long id) {
        return modelMapper.map(serviceRepository.findById(id), ServiceForSaleDto.class);
    }

    @Override
    public List<ServiceForSaleDto> getAllServices() {
        return serviceRepository
                .findAll()
                .stream()
                .map(service -> modelMapper.map(service, ServiceForSaleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ServiceForSaleDto updateService(Long id, ServiceForSaleDto serviceForSaleDto) {
        ServiceForSale serviceForSale = serviceRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Service not found"));
        BeanUtils.copyProperties(serviceForSaleDto, serviceForSale, "id");
        return modelMapper.map(serviceRepository.save(serviceForSale), ServiceForSaleDto.class);
    }
}
