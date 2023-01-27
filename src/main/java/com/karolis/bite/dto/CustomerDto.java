package com.karolis.bite.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String companiesName;
    private String personalCode;

    private String companiesCode;

    private Long accountId;

}
