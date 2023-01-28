package com.karolis.bite.dto;

import com.karolis.bite.enums.ServicesEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ServiceForSaleDto {

    private Long id;
    private String serviceName;
    @Enumerated(EnumType.STRING)
    private ServicesEnum type;
    private String description;
}
