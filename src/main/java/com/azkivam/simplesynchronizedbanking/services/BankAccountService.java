package com.azkivam.simplesynchronizedbanking.services;

import com.azkivam.simplesynchronizedbanking.entities.BankAccount;
import com.azkivam.simplesynchronizedbanking.entities.Person;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    boolean exists(BankAccount bankAccount);
    boolean exists(Long bankAccountNumber);
    BankAccount create(BankAccount bankAccount);
    BankAccount update(BankAccount bankAccount);

    List<BankAccount>getAll();
    Optional<BankAccount> fetch(Long bankAccountNumber);
}
