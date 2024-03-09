package com.azkivam.simplesynchronizedbanking.controllers;

import com.azkivam.simplesynchronizedbanking.entities.BankAccount;
import com.azkivam.simplesynchronizedbanking.entities.Person;
import com.azkivam.simplesynchronizedbanking.services.BankAccountService;
import com.azkivam.simplesynchronizedbanking.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

@ShellComponent
public class Bank {

    @Autowired
    private PersonService personService;

    @Autowired
    private BankAccountService bankAccountService;


    @ShellMethod(value = "Switch.",key = "switch")
    public void select(String switchKey){
        switch (switchKey) {
            case "1" -> {
                Runnable task1 = () -> {
                    try {
                        getPersons();
                    } catch (Exception e) {
                        System.out.println("kkkkk");
                    }
                };
                new Thread(task1).start();
            }
            case "2" -> {
                Runnable task2 = () -> {
                    try {
                        getAccounts();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                };
                new Thread(task2).start();
            }
            case "3" -> {
                System.out.println("Enter name:");
                Scanner scanner = new Scanner(System.in);
                String name = scanner.nextLine();
                Runnable task3 = () -> {
                    try {
                        createPerson(name);
                    } catch (Exception e) {
                        System.out.println("kkkkk");
                    }
                };
                new Thread(task3).start();
            }
            case "4" -> {
                Runnable task4 = () -> {
                    try {
                        getPerson();
                    } catch (Exception e) {
                        System.out.println("kkkkk");
                    }
                };
                new Thread(task4).start();
            }
            case "5" -> {
                Runnable task5 = () -> {
                    try {
                        createAccount();
                    } catch (Exception e) {
                        System.out.println("kkkkk");
                    }
                };
                new Thread(task5).start();
            }
            case "6" -> {
                Runnable task6 = () -> {
                    try {
                        deposit();
                    } catch (Exception e) {
                        System.out.println("kkkkk");
                    }
                };
                new Thread(task6).start();
            }
            case "7" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter accountNumber:");
                String accountNumber = scanner.nextLine();
                System.out.println("Enter amount:");
                String amount = scanner.nextLine();
                Runnable task7 = () -> {
                    try {
                        withdraw(accountNumber,amount);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                };
                new Thread(task7).start();
            }
            case "8" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter source Account Number:");
                String sourceAccountNumber = scanner.nextLine();
                System.out.println("Enter destination Account Number:");
                String desAccountNumber = scanner.nextLine();
                System.out.println("Enter amount:");
                String amount = scanner.nextLine();
                Runnable task8 = () -> {
                    try {
                        transfer(sourceAccountNumber,desAccountNumber,amount);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                };
                new Thread(task8).start();
            }
            case "9" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter account Number:");
                String accountNumber = scanner.nextLine();
                Runnable task9 = () -> {
                    try {
                        balance(accountNumber);
                    } catch (Exception e) {
                        System.out.println("kkkkk");
                    }
                };
                new Thread(task9).start();
            }
        }

    }
//    @ShellMethod(value = "Create Person.",key = "create-p")
    public String createPerson(String personName){
        Person person = new Person(personName);
        personService.create(person);
        return "Person created ";
    }

//    @ShellMethod(value = "List all Persons.",key = "list-p")
    public void getPersons(){
        System.out.println(personService.getAll());
    }

//    @ShellMethod(value = "Show One Person.",key = "get-p")
    public void getPerson(){
        System.out.println("Enter person Id:");
        Scanner scanner = new Scanner(System.in);
        String Id = scanner.nextLine();
        System.out.println(personService.get(Long.valueOf(Id)));
    }

//    @ShellMethod(value = "List all Accounts.",key = "list-a")
    public void getAccounts(){
        System.out.println(bankAccountService.getAll());
    }

//    @ShellMethod(value = "Create Account.",key = "create-a")
    public void createAccount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter person Id:");
        String personId = scanner.nextLine();
        System.out.println("Enter account Number:");
        String accountNumber = scanner.nextLine();
        System.out.println("Enter initial Amount:");
        String initialAmount = scanner.nextLine();
        Optional<Person> person = personService.get(Long.valueOf(personId));
        if(person.isPresent()){
            BankAccount bankAccount = new BankAccount(person.get(),Long.valueOf(accountNumber),Long.valueOf(initialAmount));
            bankAccountService.create(bankAccount);
            System.out.println("Account created for " + person.get().getName());
        }else{
            System.out.println("Account created failed");
        }
    }

//    @ShellMethod(value = "Deposit into an Account.",key = "deposit")
    public synchronized void deposit(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter account Number:");
            String accountNumber = scanner.nextLine();
            System.out.println("Enter amount:");
            String amount = scanner.nextLine();
            if(bankAccountService.exists(Long.valueOf(accountNumber))){
                Optional<BankAccount> bankAccount = bankAccountService.fetch(Long.valueOf(accountNumber));
                bankAccount.get().setBalance(bankAccount.get().getBalance()+Long.valueOf(amount));
                bankAccountService.update(bankAccount.get());
                System.out.println("Deposit into account was successful");
            }else{
                System.out.println("There is no such account!");
            }
    }

//    @ShellMethod(value = "Withdraw from an Account.",key = "withdraw")
    public synchronized void withdraw(String accountNumber,String amount){

            if(bankAccountService.exists(Long.valueOf(accountNumber))){
                Optional<BankAccount> bankAccount = bankAccountService.fetch(Long.valueOf(accountNumber));
                //here it must be checked if it is greater than zero
                bankAccount.get().setBalance(bankAccount.get().getBalance()-Long.parseLong(amount));
                bankAccountService.update(bankAccount.get());
                System.out.println("withdraw from account was successful");
            }else{
                System.out.println("There is no such account!");
            }


    }

//    @ShellMethod(value = "Transfer From One Account to Another.",key = "transfer")
    public synchronized void transfer(String sourceAccountNumber,String desAccountNumber,String amount){
            if(bankAccountService.exists(Long.valueOf(sourceAccountNumber)) && bankAccountService.exists(Long.valueOf(desAccountNumber))){
                Optional<BankAccount> sourceBankAccount = bankAccountService.fetch(Long.valueOf(sourceAccountNumber));
                Optional<BankAccount> desBankAccount = bankAccountService.fetch(Long.valueOf(desAccountNumber));
                if(sourceBankAccount.get().getBalance() >= Long.parseLong(amount) ){
                    sourceBankAccount.get().setBalance(sourceBankAccount.get().getBalance()-Long.parseLong(amount));
                    desBankAccount.get().setBalance(desBankAccount.get().getBalance()+Long.parseLong(amount));
                    bankAccountService.update(sourceBankAccount.get());
                    bankAccountService.update(desBankAccount.get());
                }else{
                    System.out.println("Not enough balance to transfer!");
                }

            }else{
                System.out.println("source bankAccount or destination bankAccount deos not exist!");
            }

    }

//    @ShellMethod(value = "Get The Balance of An Account.",key = "balance")
    public synchronized void balance(String accountNumber){
            if(bankAccountService.exists(Long.valueOf(accountNumber))){
                Optional<BankAccount> bankAccount = bankAccountService.fetch(Long.valueOf(accountNumber));
                System.out.println(String.valueOf(bankAccount.get().getBalance()));
            }
    }
}
