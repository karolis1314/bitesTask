package com.karolis.bite.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ServiceDto {

    private Long id;
    private String name;
    private String type;
    private String description;
}
