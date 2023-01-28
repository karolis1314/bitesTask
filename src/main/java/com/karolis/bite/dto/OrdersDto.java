package com.karolis.bite.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrdersDto {

        private Long id;
        private String name;
        private Long serviceId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate activeFrom = LocalDate.now();
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate activeTo = LocalDate.now().plusYears(2);
        private Long msisdnId;
}
