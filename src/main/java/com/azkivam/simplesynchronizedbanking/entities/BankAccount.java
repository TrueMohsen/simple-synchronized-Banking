package com.azkivam.simplesynchronizedbanking.entities;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long balance;

    public BankAccount(Person person,Long account_number,Long balance) {
        this.account_number = account_number;
        this.person = person;
        this.balance = balance;
    }

    @Column(unique=true)
    private Long account_number;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "person_id")
    private Person person;

    public BankAccount() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccount_number() {
        return account_number;
    }

    public void setAccount_number(Long account_number) {
        this.account_number = account_number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", balance=" + balance +
                ", account_number=" + account_number +
                ", person=" + person +
                '}';
    }
}
