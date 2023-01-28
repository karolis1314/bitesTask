package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.MsisdnDto;
import com.karolis.bite.model.Msisdn;
import com.karolis.bite.repository.MsisdnRepository;
import com.karolis.bite.service.MsisdnService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MsisdnServiceImpl implements MsisdnService {

    private final ModelMapper modelMapper;
    private MsisdnRepository msisdnRepository;

    public MsisdnServiceImpl(ModelMapper modelMapper, MsisdnRepository msisdnRepository) {
        this.modelMapper = modelMapper;
        this.msisdnRepository = msisdnRepository;
    }
    @Transactional
    @Override
    public MsisdnDto saveMsisdn(MsisdnDto msisdn) {
        return modelMapper
                .map(msisdnRepository.save(modelMapper
                        .map(msisdn, Msisdn.class)), MsisdnDto.class);
    }

    @Override
    public List<MsisdnDto> getAllMsisdns() {
        return msisdnRepository.findAll()
                .stream()
                .map(msisdn -> modelMapper.map(msisdn, MsisdnDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMsisdn(Long id) {

    }

    @Override
    public MsisdnDto getMsisdnById(Long id) {
        return msisdnRepository.findById(id)
                .map(msisdn -> modelMapper.map(msisdn, MsisdnDto.class))
                .orElse(null);
    }

    @Override
    public MsisdnDto getMsisdnByOrderId(Long orderId) {
        return null;
    }

    @Override
    public MsisdnDto updateMsisdn(Long id, MsisdnDto msisdnDto) {
        return null;
    }
}
