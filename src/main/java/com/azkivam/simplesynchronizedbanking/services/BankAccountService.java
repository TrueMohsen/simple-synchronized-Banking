package com.azkivam.simplesynchronizedbanking.services;

import com.azkivam.simplesynchronizedbanking.entities.BankAccount;
import com.azkivam.simplesynchronizedbanking.entities.Person;

import java.util.List;

public interface BankAccountService {
    boolean exists(BankAccount bankAccount);
    BankAccount create(BankAccount bankAccount);
    BankAccount update(BankAccount bankAccount);

    List<BankAccount>getAll();
}
