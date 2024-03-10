package com.azkivam.simplesynchronizedbanking.controllers;

import com.azkivam.simplesynchronizedbanking.entities.BankAccount;
import com.azkivam.simplesynchronizedbanking.entities.Person;
import com.azkivam.simplesynchronizedbanking.services.BankAccountService;
import com.azkivam.simplesynchronizedbanking.services.PersonService;
import com.azkivam.simplesynchronizedbanking.utilities.Material;
import com.azkivam.simplesynchronizedbanking.utilities.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ShellComponent
public class Bank {

    @Autowired
    private PersonService personService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private Receiver receiver;

    ExecutorService executorService = Executors.newFixedThreadPool(10);


    @ShellMethod(value = "Switch.",key = "switch")
    public void select(String switchKey){
        switch (switchKey) {
            case "1" -> executorService.execute(ListPersons());
            case "2" -> executorService.execute(listAccounts());
            case "3" -> {
//                Map<Material,Boolean> controlUnit = new HashMap<>();
//                controlUnit.put(Material.NAME,true);
//                Map<Material,String> result = receiver.receiveController(controlUnit);
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter name:");
                String name = scanner.nextLine();
//                System.out.println(result.get(Material.NAME));
                executorService.execute(createPerson(name));
            }
            case "4" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter person Id:");
                String Id = scanner.nextLine();
                executorService.execute(getPerson(Id));
            }
            case "5" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter person Id:");
                String personId = scanner.nextLine();
                System.out.println("Enter account Number:");
                String accountNumber = scanner.nextLine();
                System.out.println("Enter initial Amount:");
                String initialAmount = scanner.nextLine();
                executorService.execute(createAccount(personId,accountNumber,initialAmount));
            }

            case "6" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter account Number:");
                String accountNumber = scanner.nextLine();
                System.out.println("Enter amount:");
                String amount = scanner.nextLine();
                executorService.execute(deposit(accountNumber,amount));
            }
            case "7" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter accountNumber:");
                String accountNumber = scanner.nextLine();
                System.out.println("Enter amount:");
                String amount = scanner.nextLine();
                executorService.execute(withdrawCase(accountNumber,amount));
            }
            case "8" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter source Account Number:");
                String sourceAccountNumber = scanner.nextLine();
                System.out.println("Enter destination Account Number:");
                String desAccountNumber = scanner.nextLine();
                System.out.println("Enter amount:");
                String amount = scanner.nextLine();
                executorService.execute(transferCase(sourceAccountNumber,desAccountNumber,amount));
            }
            case "9" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter Account Number:");
                String sourceAccountNumber = scanner.nextLine();
                executorService.execute(balanceCase(sourceAccountNumber));
            }
        }

    }

    public synchronized void transfer(String sourceAccountNumber,String desAccountNumber,String amount){
            if(bankAccountService.exists(Long.valueOf(sourceAccountNumber)) && bankAccountService.exists(Long.valueOf(desAccountNumber))){
                Optional<BankAccount> sourceBankAccount = bankAccountService.fetch(Long.valueOf(sourceAccountNumber));
                Optional<BankAccount> desBankAccount = bankAccountService.fetch(Long.valueOf(desAccountNumber));
                if(sourceBankAccount.get().getBalance() >= Long.parseLong(amount) ){
                    sourceBankAccount.get().setBalance(sourceBankAccount.get().getBalance()-Long.parseLong(amount));
                    desBankAccount.get().setBalance(desBankAccount.get().getBalance()+Long.parseLong(amount));
                    bankAccountService.update(sourceBankAccount.get());
                    bankAccountService.update(desBankAccount.get());
                    System.out.println("transfer Successful!");
                }else{
                    System.out.println("Not enough balance to transfer!");
                }

            }else{
                System.out.println("source bankAccount or destination bankAccount does not exist!");
            }
    }

    public synchronized void withdraw(String accountNumber, String amount){
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


    public synchronized void balance(String accountNumber){
            if(bankAccountService.exists(Long.valueOf(accountNumber))){
                Optional<BankAccount> bankAccount = bankAccountService.fetch(Long.valueOf(accountNumber));
                System.out.println("Balance is : "+bankAccount.get().getBalance());
            }
    }

    public Runnable ListPersons(){
        return new Runnable() {
            @Override
            public void run() {
                System.out.println(personService.getAll());
            }
        };

    }

    public Runnable listAccounts(){
        return new Runnable() {
            @Override
            public void run() {
                System.out.println(bankAccountService.getAll());
            }
        };

    }
    public Runnable createPerson(String name){
        return new Runnable() {
            @Override
            public void run() {
                Person person = new Person(name);
                personService.create(person);
                System.out.println(name + " created successfully");
            }
        };

    }
    public Runnable getPerson(String id){
        return new Runnable() {
            @Override
            public void run() {
                System.out.println(personService.get(Long.valueOf(id)));
            }
        };

    }
    public Runnable createAccount(String personId,String accountNumber,String initialAmount){
        return new Runnable() {
            @Override
            public void run() {
                Optional<Person> person = personService.get(Long.valueOf(personId));
                if(person.isPresent()){
                    BankAccount bankAccount = new BankAccount(person.get(),Long.valueOf(accountNumber),Long.valueOf(initialAmount));
                    bankAccountService.create(bankAccount);
                    System.out.println("Account created for " + person.get().getName());
                }else{
                    System.out.println("Account created failed");
                }
            }
        };

    }

    public Runnable deposit(String accountNumber,String amount){
        return new Runnable() {
            @Override
            public void run() {
                if(bankAccountService.exists(Long.valueOf(accountNumber))){
                    Optional<BankAccount> bankAccount = bankAccountService.fetch(Long.valueOf(accountNumber));
                    bankAccount.get().setBalance(bankAccount.get().getBalance()+Long.valueOf(amount));
                    bankAccountService.update(bankAccount.get());
                    System.out.println("Deposit into account was successful");
                }else{
                    System.out.println("There is no such account!");
                }
            }
        };

    }
    public Runnable withdrawCase(String accountNumber,String amount){
        return new Runnable() {
            @Override
            public void run() {
                withdraw(accountNumber, amount);
            }
        };

    }
    public Runnable transferCase(String sourceAccountNumber,String desAccountNumber,String amount){
        return new Runnable() {
            @Override
            public void run() {
                transfer(sourceAccountNumber, desAccountNumber, amount);
            }
        };

    }

    public Runnable balanceCase(String sourceAccountNumber){
        return new Runnable() {
            @Override
            public void run() {
                balance(sourceAccountNumber);
            }
        };

    }
}
