package com.karolis.bite.service.serviceImpl;

import com.karolis.bite.dto.MsisdnDto;
import com.karolis.bite.exceptions.NotFoundException;
import com.karolis.bite.exceptions.PropertyValueException;
import com.karolis.bite.exceptions.ServerErrorException;
import com.karolis.bite.model.Account;
import com.karolis.bite.model.Msisdn;
import com.karolis.bite.repository.MsisdnRepository;
import com.karolis.bite.service.MsisdnService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.karolis.bite.Constants.CommonMethods.formatErrorMessageForConstantMessage;
import static com.karolis.bite.Constants.GeneralErrorMessages.*;
import static com.karolis.bite.Constants.MsisdnExceptionConstants.*;

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
        } catch (DataIntegrityViolationException e) {
                throw new NotFoundException(formatErrorMessageForConstantMessage(MSISDN_NOT_FOUND_BY_ACCOUNT_ID, msisdn.getAccountId()));
        } catch (org.hibernate.PropertyValueException e) {
            throw new PropertyValueException(PROPERTY_VALUE_ERROR);
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
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
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public void deleteMsisdn(Long id) {
        try {
            msisdnRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(MSISDN_NOT_FOUND, id));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public MsisdnDto getMsisdnById(Long id) {
        try {
            return msisdnRepository.findById(id)
                    .map(msisdn -> modelMapper.map(msisdn, MsisdnDto.class))
                    .orElseThrow(() -> new DataIntegrityViolationException(formatErrorMessageForConstantMessage(MSISDN_NOT_FOUND, id)));
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(MSISDN_NOT_FOUND, id));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public MsisdnDto getMsisdnByOrderId(Long orderId) {
        try {
            return modelMapper.map(msisdnRepository.findAll().stream()
                    .filter(order -> order.getOrders().stream().anyMatch(o -> o.getId().equals(orderId)))
                    .findFirst()
                    .orElseThrow(() -> new DataIntegrityViolationException(formatErrorMessageForConstantMessage(MSISDN_NOT_FOUND_BY_ORDER_ID, orderId))), MsisdnDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(MSISDN_NOT_FOUND_BY_ORDER_ID, orderId));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public MsisdnDto updateMsisdnExtendTwoYears(Long id, MsisdnDto msisdnDto) {
        try {
            Msisdn msisdn = msisdnRepository.findById(id)
                    .orElseThrow(() -> new DataIntegrityViolationException(formatErrorMessageForConstantMessage(MSISDN_NOT_FOUND, id)));
            BeanUtils.copyProperties(msisdnDto, msisdn, "id", "activeFrom", "accountId");
            final long differenceInYears= ChronoUnit.YEARS.between(msisdn.getActiveFrom(), msisdnDto.getActiveTo());
            if( differenceInYears == 2) {
                msisdn.setActiveTo(msisdnDto.getActiveTo().plusYears(2));
            }
            return modelMapper.map(msisdnRepository.save(msisdn), MsisdnDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(MSISDN_NOT_FOUND, id));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
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
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(formatErrorMessageForConstantMessage(MSISDN_NOT_FOUND_BY_ACCOUNT_ID, accountId));
        } catch (Exception e) {
            throw new ServerErrorException(SERVER_ERROR);
        }
    }
}
