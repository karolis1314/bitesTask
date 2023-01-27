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
@Table(name = "customer")
public class Customer {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "companiesName", nullable = false)
    private String companiesName;

    @Column(name = "companiesCode", nullable = false)
    private String companiesCode;

    @Column(name = "personalCode", nullable = false)
    private String personalCode;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

}
