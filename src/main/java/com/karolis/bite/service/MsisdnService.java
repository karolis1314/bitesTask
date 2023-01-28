package com.karolis.bite.service;

import com.karolis.bite.dto.MsisdnDto;

import java.util.List;

public interface MsisdnService {

    MsisdnDto saveMsisdn(MsisdnDto msisdn);
    
    List<MsisdnDto> getAllMsisdns();

    void deleteMsisdn(Long id);

    MsisdnDto getMsisdnById(Long id);

    MsisdnDto getMsisdnByOrderId(Long orderId);

    MsisdnDto updateMsisdn(Long id, MsisdnDto msisdnDto);

    List<MsisdnDto> getAllMsisdnsByAccountId(Long accountId);
}
