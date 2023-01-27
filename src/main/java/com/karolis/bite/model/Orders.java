package com.karolis.bite.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "orders")
    private List<Service> services;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msisdn_id")
    private Msisdn msisdn;

}
