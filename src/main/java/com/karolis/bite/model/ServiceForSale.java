package com.karolis.bite.model;

import com.karolis.bite.enums.ServicesEnum;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service")
public class ServiceForSale {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serviceName", nullable = false)
    private String serviceName;

    @Column(name = "type", nullable = false)
    private ServicesEnum type;

    @Column(name = "description", nullable = false)
    private String description;

}
