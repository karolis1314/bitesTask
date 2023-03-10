package com.karolis.bite.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "orders")
public class Orders {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "activeFrom", nullable = false)
    private LocalDate activeFrom;

    @Column(name = "activeTo", nullable = false)
    private LocalDate activeTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msisdnId")
    private Msisdn msisdn;

    private Long serviceId;

}
