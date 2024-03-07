package com.azkivam.simplesynchronizedbanking.controllers;

import com.azkivam.simplesynchronizedbanking.entities.Person;
import com.azkivam.simplesynchronizedbanking.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Bank {

    @Autowired
    private PersonService personService;

    @ShellMethod(value = "Create Person.",key = "create-p")
    public String createPerson(String personName){
        Person person = new Person(personName);
        personService.create(person);
        return "Person created " + personName;
    }

    @ShellMethod(value = "Create Account.",key = "create-a")
    public String createAccount(String personName){
        return "Account created for " + personName;
    }

    @ShellMethod(value = "Deposit into an Account.",key = "deposit")
    public String deposit(@ShellOption(defaultValue = "spring")String arg){
        return "Hello world " + arg;
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
