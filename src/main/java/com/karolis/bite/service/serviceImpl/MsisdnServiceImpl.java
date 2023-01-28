package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.MsisdnDto;
import com.karolis.bite.model.Msisdn;
import com.karolis.bite.repository.MsisdnRepository;
import com.karolis.bite.service.MsisdnService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MsisdnServiceImpl implements MsisdnService {

    @Bean
    private ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private MsisdnRepository msisdnRepository;

    public MsisdnServiceImpl(MsisdnRepository msisdnRepository) {
        this.msisdnRepository = msisdnRepository;
    }
    @Transactional
    @Override
    public MsisdnDto saveMsisdn(MsisdnDto msisdn) {
        return modelMapper()
                .map(msisdnRepository.save(modelMapper()
                        .map(msisdn, Msisdn.class)), MsisdnDto.class);
    }

    @Override
    public void deleteMsisdn(Long id) {

    }

    @Override
    public MsisdnDto getMsisdnById(Long id) {
        return msisdnRepository.findById(id)
                .map(msisdn -> modelMapper().map(msisdn, MsisdnDto.class))
                .orElse(null);
    }

    @Override
    public MsisdnDto getMsisdnByOrderId(Long orderId) {
        return null;
    }
}
