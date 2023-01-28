package com.karolis.bite.dto;

import lombok.Data;

@Data
public class AccountDto {

    private Long id;
    private String name;
    private String address;
    private Long customerId;

}
