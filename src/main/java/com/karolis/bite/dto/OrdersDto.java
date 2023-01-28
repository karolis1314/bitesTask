package com.karolis.bite.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrdersDto {

        private Long id;
        private String name;
        private Long serviceId;
        private LocalDate activeFrom;
        private LocalDate activeTo;
}
