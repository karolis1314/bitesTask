package com.karolis.bite.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ServicesEnum {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    BLOCKED("Blocked");
    private String status;

    ServicesEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
