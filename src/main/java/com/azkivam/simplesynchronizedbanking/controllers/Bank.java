package com.azkivam.simplesynchronizedbanking.controllers;

import com.azkivam.simplesynchronizedbanking.entities.BankAccount;
import com.azkivam.simplesynchronizedbanking.entities.Person;
import com.azkivam.simplesynchronizedbanking.services.BankAccountService;
import com.azkivam.simplesynchronizedbanking.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class Bank {

    @Autowired
    private PersonService personService;

    @Autowired
    private BankAccountService bankAccountService;


    @ShellMethod(value = "Create Person.",key = "create-p")
    public String createPerson(String personName){
        Person person = new Person(personName);
        personService.create(person);
        return "Person created ";
    }

    @ShellMethod(value = "List all Persons.",key = "list-p")
    public List<Person> getPersons(){
        return personService.getAll();
    }

    @ShellMethod(value = "Show One Person.",key = "get-p")
    public Optional<Person> getPerson(String personId){
        return personService.get(Long.valueOf(personId));
    }

    @ShellMethod(value = "List all Accounts.",key = "list-a")
    public List<BankAccount> getAccounts(){
        return bankAccountService.getAll();
    }

    @ShellMethod(value = "Create Account.",key = "create-a")
    public String createAccount(String personId,String accountNumber, String initialAmount){
        Optional<Person> person = personService.get(Long.valueOf(personId));
        if(person.isPresent()){
            BankAccount bankAccount = new BankAccount(person.get(),Long.valueOf(accountNumber),Long.valueOf(initialAmount));
            bankAccountService.create(bankAccount);
            return "Account created for " + person.get().getName();
        }else{
            return "Error";
        }
    }

    @ShellMethod(value = "Deposit into an Account.",key = "deposit")
    public String deposit(String accountNumber, String Amount){
        if(bankAccountService.exists(Long.valueOf(accountNumber))){
            Optional<BankAccount> bankAccount = bankAccountService.fetch(Long.valueOf(accountNumber));
            bankAccount.get().setBalance(Long.valueOf(Amount));
            bankAccountService.update(bankAccount.get());
            return "Deposit into account was successful";
        }else{
            return "There is no such account!";
        }
    }

    @ShellMethod(value = "Withdraw from an Account.",key = "withdraw")
    public String withdraw(@ShellOption(defaultValue = "spring")String arg){
        return "Hello world " + arg;
    }

    @ShellMethod(value = "Transfer From One Account to Another.",key = "transfer")
    public String transfer(@ShellOption(defaultValue = "spring")String arg){
        return "Hello world " + arg;
    }

    @ShellMethod(value = "Get The Balance of An Account.",key = "balance")
    public String balance(@ShellOption(defaultValue = "spring")String arg){
        return "Hello world " + arg;
    }

}
