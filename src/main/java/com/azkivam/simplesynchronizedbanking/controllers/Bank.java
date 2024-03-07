package com.azkivam.simplesynchronizedbanking.controllers;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Bank {

    @ShellMethod(value = "Create Account.",key = "create")
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
