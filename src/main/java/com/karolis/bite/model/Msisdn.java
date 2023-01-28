package com.karolis.bite.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "activeFrom", nullable = false)
    private LocalDate activeFrom;

    @Column(name = "activeTo", nullable = false)
    private LocalDate activeTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "msisdn", cascade = CascadeType.ALL)
    private List<Orders> orders;
}
