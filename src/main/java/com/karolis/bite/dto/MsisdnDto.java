package com.karolis.bite.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MsisdnDto {

        private Long id;
        private String activeFrom;
        private String activeTo;
        private Long msisdnId;
}
