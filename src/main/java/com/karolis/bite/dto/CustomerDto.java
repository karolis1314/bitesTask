package com.karolis.bite.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String companiesName;
    private String companiesCode;
    private String personalCode;

}
