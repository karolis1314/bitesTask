package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.ServiceForSaleDto;
import com.karolis.bite.enums.ServicesEnum;
import com.karolis.bite.exceptions.NotFoundException;
import com.karolis.bite.exceptions.PropertyValueException;
import com.karolis.bite.exceptions.ServerErrorException;
import com.karolis.bite.model.ServiceForSale;
import com.karolis.bite.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.RollbackException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceForSaleImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ServiceForSaleImpl serviceForSaleImpl;

    @Test
    void saveService_ShouldSaveService_WhenValidServiceTypeIsProvided() {

        ServiceForSaleDto serviceForSaleDto = new ServiceForSaleDto();
        serviceForSaleDto.setId(1L);
        serviceForSaleDto.setType(ServicesEnum.ACTIVE);

        ServiceForSale serviceForSale = new ServiceForSale();
        serviceForSale.setId(1L);
        serviceForSale.setType(ServicesEnum.ACTIVE);

        when(modelMapper.map(serviceForSaleDto, ServiceForSale.class)).thenReturn(serviceForSale);
        when(serviceRepository.save(serviceForSale)).thenReturn(serviceForSale);
        when(modelMapper.map(serviceForSale, ServiceForSaleDto.class)).thenReturn(serviceForSaleDto);

        ServiceForSaleDto savedServiceForSale = serviceForSaleImpl.saveService(serviceForSaleDto);

        assertThat(savedServiceForSale).isEqualToComparingFieldByField(serviceForSaleDto);
    }

    @Test
    void saveService_ShouldThrowDataIntegrityViolationException_WhenServiceNotFound() {
        ServiceForSaleDto serviceForSaleDto = new ServiceForSaleDto();
        serviceForSaleDto.setId(1L);
        serviceForSaleDto.setType(ServicesEnum.ACTIVE);
        ServiceForSale serviceForSale = new ServiceForSale();
        serviceForSale.setId(1L);
        serviceForSale.setType(ServicesEnum.ACTIVE);

        when(modelMapper.map(serviceForSaleDto, ServiceForSale.class)).thenReturn(serviceForSale);
        when(serviceRepository.save(serviceForSale)).thenThrow(new DataIntegrityViolationException("Service not found"));

        assertThrows(NotFoundException.class, () -> serviceForSaleImpl.saveService(serviceForSaleDto));
    }

    @Test
    void saveService_ShouldThrowPropertyValueException_WhenInvalidType() {
        
        ServiceForSaleDto serviceForSaleDto = new ServiceForSaleDto();
        serviceForSaleDto.setId(1L);

        ServiceForSale serviceForSale = new ServiceForSale();
        serviceForSale.setId(1L);

        when(modelMapper.map(serviceForSaleDto, ServiceForSale.class)).thenReturn(serviceForSale);
        doThrow(new org.hibernate.PropertyValueException("Invalid property","", "")).when(serviceRepository).save(serviceForSale);

        assertThrows(PropertyValueException.class, () -> serviceForSaleImpl.saveService(serviceForSaleDto));
    }

    @Test
    void saveService_ShouldThrowServerErrorException_WhenRollbackExceptionOccurs() {
        
        ServiceForSaleDto serviceForSaleDto = new ServiceForSaleDto();
        serviceForSaleDto.setId(1L);
        serviceForSaleDto.setType(ServicesEnum.ACTIVE);

        ServiceForSale serviceForSale = new ServiceForSale();
        serviceForSale.setId(1L);
        serviceForSale.setType(ServicesEnum.ACTIVE);

        when(modelMapper.map(serviceForSaleDto, ServiceForSale.class)).thenReturn(serviceForSale);
        when(serviceRepository.save(serviceForSale)).thenThrow(new RollbackException("Rollback error"));

        assertThrows(ServerErrorException.class, () -> serviceForSaleImpl.saveService(serviceForSaleDto));
    }


    @Test
    void deleteService_ShouldThrowServerErrorException_WhenExceptionOccurs() {
        
        Long id = 1L;
        doThrow(new RuntimeException())
                .when(serviceRepository)
                .deleteById(id);

        assertThrows(ServerErrorException.class, () -> serviceForSaleImpl.deleteService(id));
    }

    @Test
    void getServiceById_ShouldReturnService_WhenValidIdIsProvided() {

        Long id = 1L;
        ServiceForSale serviceForSale = new ServiceForSale();
        serviceForSale.setId(id);
        ServiceForSaleDto serviceForSaleDto = new ServiceForSaleDto();
        serviceForSaleDto.setId(id);
        when(serviceRepository.findById(id))
                .thenReturn(Optional.of(serviceForSale));
        when(modelMapper.map(serviceForSale, ServiceForSaleDto.class))
                .thenReturn(serviceForSaleDto);


        ServiceForSaleDto returnedService = serviceForSaleImpl.getServiceById(id);


        assertThat(returnedService).isEqualToComparingFieldByField(serviceForSaleDto);
    }

    @Test
    void getServiceById_ShouldThrowServerErrorException_WhenServiceNotFound() {

        Long id = 1L;
        when(serviceRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ServerErrorException.class, () -> serviceForSaleImpl.getServiceById(id));
    }

}
