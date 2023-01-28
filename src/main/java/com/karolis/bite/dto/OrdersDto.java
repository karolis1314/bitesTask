package com.karolis.bite.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class OrdersDto {

        private Long id;
        private String name;
        private Long serviceId;
}
