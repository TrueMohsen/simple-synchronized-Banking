package com.azkivam.simplesynchronizedbanking.controllers;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Bank {

    @ShellMethod(key = "hello-world")
    public String helloWorld(@ShellOption(defaultValue = "spring")String arg){
        return "Hello world " + arg;
    }

    @ShellMethod(key = "create")
    public String createAccount(String arg){
        return "Hello world " + arg;
    }

    @ShellMethod(key = "deposit")
    public String deposit(@ShellOption(defaultValue = "spring")String arg){
        return "Hello world " + arg;
    }

    @ShellMethod(key = "withdraw")
    public String withdraw(@ShellOption(defaultValue = "spring")String arg){
        return "Hello world " + arg;
    }

    @ShellMethod(key = "transfer")
    public String transfer(@ShellOption(defaultValue = "spring")String arg){
        return "Hello world " + arg;
    }

    @ShellMethod(key = "balance")
    public String balance(@ShellOption(defaultValue = "spring")String arg){
        return "Hello world " + arg;
    }

}
