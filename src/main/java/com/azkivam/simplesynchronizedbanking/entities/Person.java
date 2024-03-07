package com.azkivam.simplesynchronizedbanking.entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "person")
    private Set<BankAccount> accountSet = new HashSet<>();


}
