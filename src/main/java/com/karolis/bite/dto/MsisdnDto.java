package com.karolis.bite.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MsisdnDto {

        private Long id;
        private LocalDate activeFrom;
        private LocalDate activeTo;
}
