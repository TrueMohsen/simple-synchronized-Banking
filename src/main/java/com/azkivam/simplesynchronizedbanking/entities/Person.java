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

    public Person(String name) {
        this.name = name;
    }

    private String name;

    @OneToMany(mappedBy = "person")
    private Set<BankAccount> accountSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BankAccount> getAccountSet() {
        return accountSet;
    }

    public void setAccountSet(Set<BankAccount> accountSet) {
        this.accountSet = accountSet;
    }
}
