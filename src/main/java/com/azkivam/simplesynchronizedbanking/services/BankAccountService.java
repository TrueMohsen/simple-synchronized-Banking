package com.azkivam.simplesynchronizedbanking.services;

import com.azkivam.simplesynchronizedbanking.entities.BankAccount;
import com.azkivam.simplesynchronizedbanking.entities.Person;

public interface BankAccountService {
    boolean exists(BankAccount bankAccount);
    BankAccount create(BankAccount bankAccount);
    BankAccount update(BankAccount bankAccount);
}
