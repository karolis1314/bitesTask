package com.karolis.bite.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "service_for_sale")
public class ServiceForSale {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serviceName", nullable = false)
    private String serviceName;

    @Column(name = "type", nullable = false)
    private Enum type;

    @Column(name = "description", nullable = false)
    private String description;

}
