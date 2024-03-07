package com.azkivam.simplesynchronizedbanking.services;


import com.azkivam.simplesynchronizedbanking.entities.BankAccount;
import com.azkivam.simplesynchronizedbanking.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl implements BankAccountService{

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public boolean exists(BankAccount bankAccount) {
        return bankAccountRepository.existsById(bankAccount.getAccount_number());
    }

    @Override
    public BankAccount create(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
        return bankAccount;
    }

    @Override
    public BankAccount update(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
        return bankAccount;
    }
}
