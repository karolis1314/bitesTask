package com.karolis.bite.service;

import com.karolis.bite.dto.MsisdnDto;

public interface MsisdnService {

    void saveMsisdn(MsisdnDto msisdn);

    void deleteMsisdn(Long id);

    MsisdnDto getMsisdnById(Long id);

    MsisdnDto getMsisdnByOrderId(Long orderId);
}
