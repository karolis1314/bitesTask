package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.MsisdnDto;
import com.karolis.bite.exceptions.NotFoundException;
import com.karolis.bite.model.Account;
import com.karolis.bite.model.Msisdn;
import com.karolis.bite.repository.MsisdnRepository;
import com.karolis.bite.service.MsisdnService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MsisdnServiceImpl implements MsisdnService {

    private final ModelMapper modelMapper;
    private final MsisdnRepository msisdnRepository;

    private final AccountServiceImpl accountService;

    @Autowired
    public MsisdnServiceImpl(ModelMapper modelMapper, MsisdnRepository msisdnRepository, AccountServiceImpl accountService) {
        this.modelMapper = modelMapper;
        this.msisdnRepository = msisdnRepository;
        this.accountService = accountService;
    }
    @Transactional
    @Override
    public MsisdnDto saveMsisdn(MsisdnDto msisdn) {
        try {
            Msisdn ms = modelMapper.map(msisdn, Msisdn.class);
            Account acc = accountService.findAccountById(msisdn.getAccountId());
            ms.setAccount(acc);
            ms = msisdnRepository.save(ms);
            return modelMapper.map(ms, MsisdnDto.class);
        } catch (Exception e) {
            throw new NotFoundException("Account with number: " + msisdn.getAccountId() + " was not found.");
        }
    }

    @Transactional
    @Override
    public List<MsisdnDto> getAllMsisdns() {
        try {
            return msisdnRepository.findAll()
                    .stream()
                    .map(msisdn -> modelMapper.map(msisdn, MsisdnDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new NotFoundException("No msisdns found");
        }
    }

    @Transactional
    @Override
    public void deleteMsisdn(Long id) {
        try {
            msisdnRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Msisdn with id: " + id + " was not found.");
        }
    }

    @Transactional
    @Override
    public MsisdnDto getMsisdnById(Long id) {
        try {
            return msisdnRepository.findById(id)
                    .map(msisdn -> modelMapper.map(msisdn, MsisdnDto.class))
                    .orElse(null);
        } catch (Exception e) {
            throw new NotFoundException("Msisdn with id: " + id + " was not found.");
        }
    }

    @Transactional
    @Override
    public MsisdnDto getMsisdnByOrderId(Long orderId) {
        try {
            return modelMapper.map(msisdnRepository.findByOrderId(orderId), MsisdnDto.class);
        } catch (Exception e) {
            throw new NotFoundException("Msisdn with order id: " + orderId + " was not found.");
        }
    }

    @Transactional
    @Override
    public MsisdnDto updateMsisdn(Long id, MsisdnDto msisdnDto) {
        try {
            Msisdn msisdn = msisdnRepository.findById(id).orElse(null);
            BeanUtils.copyProperties(msisdnDto, msisdn, "id", "activeFrom", "accountId");
            return modelMapper.map(msisdnRepository.save(msisdn), MsisdnDto.class);
        } catch (Exception e) {
            throw new NotFoundException("Msisdn with id: " + id + " was not found.");
        }
    }

    @Transactional
    @Override
    public List<MsisdnDto> getAllMsisdnsByAccountId(Long accountId) {
        try {
            return msisdnRepository.findAllByAccountId(accountId)
                    .stream()
                    .map(msisdn -> modelMapper.map(msisdn, MsisdnDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new NotFoundException("No msisdns found for account with id: " + accountId);
        }
    }
}
