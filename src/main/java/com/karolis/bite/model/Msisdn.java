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
@Table(name = "msisdn")
public class Msisdn {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "activeFrom", nullable = false)
    private String activeFrom;

    @Column(name = "activeTo", nullable = false)
    private String activeTo;

    @OneToMany(mappedBy = "msisdn")
    private List<Orders> orders;
}
