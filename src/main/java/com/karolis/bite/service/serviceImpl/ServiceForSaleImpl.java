package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.ServiceForSaleDto;
import com.karolis.bite.enums.ServicesEnum;
import com.karolis.bite.exceptions.NotFoundException;
import com.karolis.bite.exceptions.PropertyValueException;
import com.karolis.bite.exceptions.ServerErrorException;
import com.karolis.bite.model.ServiceForSale;
import com.karolis.bite.repository.ServiceRepository;
import com.karolis.bite.service.ServiceForSaleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.karolis.bite.Constants.CommonMethods.formatErrorMessageForConstantMessage;
import static com.karolis.bite.Constants.GeneralErrorMessages.PROPERTY_VALUE_ERROR;
import static com.karolis.bite.Constants.GeneralErrorMessages.SERVER_ERROR;
import static com.karolis.bite.configuration.LogConfig.logger;

@Service
public class ServiceForSaleImpl implements ServiceForSaleService {

    private final ModelMapper modelMapper;

    private final ServiceRepository serviceRepository;

    private final String SERVICE_NOT_FOUND = "Service with id %s not found";

    private final KafkaTemplate<String, String> kafkaTemplate;


    @Autowired
    public ServiceForSaleImpl(ModelMapper modelMapper, ServiceRepository serviceRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.modelMapper = modelMapper;
        this.serviceRepository = serviceRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    @Override
    public ServiceForSaleDto saveService(ServiceForSaleDto service) {
        try {
            return modelMapper.map(serviceRepository.save(modelMapper.map(service, ServiceForSale.class)), ServiceForSaleDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(SERVICE_NOT_FOUND, service.getId()));
        } catch (org.hibernate.PropertyValueException e) {
            throw new PropertyValueException(PROPERTY_VALUE_ERROR);
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public void deleteService(Long id) {
        try {
            serviceRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(SERVICE_NOT_FOUND, id));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ServiceForSaleDto getServiceById(Long id) {
        try {
            return modelMapper.map(serviceRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(formatErrorMessageForConstantMessage(SERVICE_NOT_FOUND, id))), ServiceForSaleDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(SERVICE_NOT_FOUND, id));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Override
    public List<ServiceForSaleDto> getAllServices() {
        try {
            kafkaTemplateForSuccessful("Successfully got all data from services table");
            return serviceRepository
                    .findAll()
                    .stream()
                    .map(service -> modelMapper.map(service, ServiceForSaleDto.class)).collect(Collectors.toList());
        } catch (Exception e) {
            kafkaTemplateForServerError();
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Override
    public ServiceForSaleDto updateService(Long id, ServiceForSaleDto serviceForSaleDto) {
        try {
            kafkaTemplateForSuccessful("Successfully updated data in services table");
            return updateChoice(id, serviceForSaleDto, "id");
        } catch (DataIntegrityViolationException e) {
            kafkaTemplateForData();
            throw new NotFoundException(formatErrorMessageForConstantMessage(SERVICE_NOT_FOUND, id));
        } catch (org.hibernate.PropertyValueException e) {
            kafkaTemplateForPropertyValue();
            throw new PropertyValueException(PROPERTY_VALUE_ERROR);
        } catch (Exception e) {
            kafkaTemplateForServerError();
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Override
    public ServiceForSaleDto updateServiceStatus(Long id, ServiceForSaleDto serviceForSaleDto) {
        try {
            kafkaTemplateForSuccessful("Successfully updated status in services table");
            return updateChoice(id, serviceForSaleDto, "id", "serviceName", "description");
        } catch (DataIntegrityViolationException e) {
            kafkaTemplateForData();
            throw new NotFoundException(formatErrorMessageForConstantMessage(SERVICE_NOT_FOUND, id));
        } catch (org.hibernate.PropertyValueException e) {
            kafkaTemplateForPropertyValue();
            throw new PropertyValueException(PROPERTY_VALUE_ERROR);
        } catch (Exception e) {
            kafkaTemplateForServerError();
            throw new ServerErrorException(SERVER_ERROR);
        }
    }


    @Transactional
    @Override
    public void createServices() {
        try {
            serviceRepository.save(new ServiceForSale(1L, "5G", ServicesEnum.ACTIVE,"Best 5G services."));
            serviceRepository.save(new ServiceForSale(2L, "4G", ServicesEnum.ACTIVE,"Best 4G services."));
            serviceRepository.save(new ServiceForSale(3L, "3G", ServicesEnum.ACTIVE,"Best 3G services."));
            serviceRepository.save(new ServiceForSale(4L, "2G", ServicesEnum.ACTIVE,"Best 2G services."));
            logger.info("Services created");
            kafkaTemplateForSuccessful("Services created");
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    private ServiceForSaleDto updateChoice(Long id, ServiceForSaleDto serviceForSaleDto, String... types) {
        ServiceForSale serviceForSale = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(formatErrorMessageForConstantMessage(SERVICE_NOT_FOUND, id)));
        BeanUtils.copyProperties(serviceForSaleDto, serviceForSale, types);
        kafkaTemplateForSuccessful("Successfully updated data in services table");
        return modelMapper.map(serviceRepository.save(serviceForSale), ServiceForSaleDto.class);
    }

    private void kafkaTemplateForServerError() {
        kafkaTemplate.send("account", "Server Error");
    }

    private void kafkaTemplateForPropertyValue() {
        kafkaTemplate.send("account", "Property value exception");
    }

    private void kafkaTemplateForData() {
        kafkaTemplate.send("account", "Data integrity violation");
    }

    private void kafkaTemplateForSuccessful(String msg) {
        kafkaTemplate.send("account", msg);
    }
}
