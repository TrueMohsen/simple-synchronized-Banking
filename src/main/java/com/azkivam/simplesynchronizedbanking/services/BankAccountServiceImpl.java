package com.azkivam.simplesynchronizedbanking.services;


import com.azkivam.simplesynchronizedbanking.entities.BankAccount;
import com.azkivam.simplesynchronizedbanking.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService{

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public boolean exists(BankAccount bankAccount) {
        return bankAccountRepository.existsById(bankAccount.getAccount_number());
    }

    @Override
    public boolean exists(Long bankAccountNumber) {
        return bankAccountRepository.existsById(bankAccountNumber);
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

    @Override
    public List<BankAccount> getAll() {
        return bankAccountRepository.findAll();
    }

    public Optional<BankAccount> fetch(Long bankAccountNumber) {
        return bankAccountRepository.findById(bankAccountNumber);
    }

    @Override
    public Optional<BankAccount> fetchByAccountNumber(Long bankAccountNumber) {
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        for(BankAccount ba:bankAccountList){
            if(Objects.equals(ba.getAccount_number(), bankAccountNumber)){
                return Optional.of(ba);
            }
        }
        return Optional.empty();
    }
}
