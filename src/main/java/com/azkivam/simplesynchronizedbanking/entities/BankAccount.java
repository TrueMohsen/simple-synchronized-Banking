package com.azkivam.simplesynchronizedbanking.entities;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private Long account_number;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "person_id")
    private Person person;


}
