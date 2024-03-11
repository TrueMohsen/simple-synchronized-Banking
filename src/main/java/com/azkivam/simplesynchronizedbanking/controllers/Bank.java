package com.azkivam.simplesynchronizedbanking.controllers;

import com.azkivam.simplesynchronizedbanking.entities.BankAccount;
import com.azkivam.simplesynchronizedbanking.entities.Person;
import com.azkivam.simplesynchronizedbanking.services.BankAccountService;
import com.azkivam.simplesynchronizedbanking.services.PersonService;
import com.azkivam.simplesynchronizedbanking.utilities.Material;
import com.azkivam.simplesynchronizedbanking.utilities.Receiver;
import com.azkivam.simplesynchronizedbanking.utilities.Subject;
import com.azkivam.simplesynchronizedbanking.utilities.TransactionObserver;
import com.azkivam.simplesynchronizedbanking.validators.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ShellComponent
public class Bank implements Subject {

    @Autowired
    private PersonService personService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private Receiver receiver;

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    List<TransactionObserver> observers;

    @Autowired
    private InputValidator validator;


    Map<Material,String> state = new HashMap<>();
    @ShellMethod(value = "Switch.",key = "switch")
    public void select(String switchKey) throws IOException {
        switch (switchKey) {
            case "1" -> {
                setState("None ","List of Persons","None");
                Notify();
                executorService.execute(ListPersons());
            }
            case "2" -> {
                setState("None ","List of Accounts","None");
                Notify();
                executorService.execute(listAccounts());
            }
            case "3" -> {
//                Map<Material,Boolean> controlUnit = new HashMap<>();
//                controlUnit.put(Material.NAME,true);
//                Map<Material,String> result = receiver.receiveController(controlUnit);
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter name:");
                String name = scanner.nextLine();
//                System.out.println(result.get(Material.NAME));
                setState("None ","Create Person","None");
                Notify();
                executorService.execute(createPerson(name));
            }
            case "4" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter person Id:");
                String Id = scanner.nextLine();
                setState("None ","Get Person","None");
                Notify();
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
                setState("None ","Create Account","None");
                Notify();
                executorService.execute(createAccount(personId,accountNumber,initialAmount));
            }

            case "6" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter account Number:");
                String accountNumber = scanner.nextLine();
                System.out.println("Enter amount:");
                String amount = scanner.nextLine();
                setState(accountNumber,"Deposit",amount);
                Notify();
                executorService.execute(depositCase(accountNumber,amount));
            }
            case "7" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter accountNumber:");
                String accountNumber = scanner.nextLine();
                System.out.println("Enter amount:");
                String amount = scanner.nextLine();
                setState(accountNumber,"withdraw",amount);
                Notify();
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
                setState(sourceAccountNumber+" to "+desAccountNumber,"transfer",amount);
                Notify();
                executorService.execute(transferCase(sourceAccountNumber,desAccountNumber,amount));
            }
            case "9" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter Account Number:");
                String sourceAccountNumber = scanner.nextLine();
                setState(sourceAccountNumber,"balance","None");
                Notify();
                executorService.execute(balanceCase(sourceAccountNumber));
            }
            case "10" -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter Person Id:");
                String personID = scanner.nextLine();
                setState(" None "," Delete Person "," None ");
                Notify();
                executorService.execute(deletePersonCase(personID));
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
        Optional<BankAccount> bankAccount = bankAccountService.fetchByAccountNumber(Long.valueOf(accountNumber));
        if(bankAccount.isPresent()){

            //here it must be checked if it is greater than zero
            bankAccount.get().setBalance(bankAccount.get().getBalance()-Long.parseLong(amount));
            bankAccountService.update(bankAccount.get());
            System.out.println("withdraw from account was successful");
        }else{
            System.out.println("There is no such account!");
        }
    }

    public synchronized void deposit(String accountNumber, String amount){
        Optional<BankAccount> bankAccount = bankAccountService.fetchByAccountNumber(Long.valueOf(accountNumber));

        if(bankAccount.isPresent()){
            bankAccount.get().setBalance(bankAccount.get().getBalance()+Long.parseLong(amount));
            bankAccountService.update(bankAccount.get());
            System.out.println("Deposit into account was successful");
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
                if (validator.checkNameLength(name)){
                    Person person = new Person(name);
                    personService.create(person);
                    System.out.println(name + " created successfully");
                }else{
                    System.out.println(name + " is not a valid name! It must be between 3 and 11 characters long.");
                }

            }
        };

    }
    public Runnable getPerson(String id){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Optional<Person> person = personService.get(Long.valueOf(id));
                    if(person.isPresent()){
                        System.out.println(person.get());
                    }else{
                        System.out.println("No such person is available!");
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
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
                    try{
                        bankAccountService.create(bankAccount);
                        System.out.println("Account created for " + person.get().getName() +" Successfully!");
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }else{
                    System.out.println("Account creation failed");
                }
            }
        };

    }

    public Runnable depositCase(String accountNumber,String amount){
        return new Runnable() {
            @Override
            public void run() {
                deposit(accountNumber,amount);
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
    public Runnable deletePersonCase(String personID){
        return new Runnable() {
            @Override
            public void run() {
                try{
                    Long personId = Long.valueOf(personID);
                    Optional<Person> person = personService.get(personId);
                    if(person.isPresent()){
                        personService.deletePerson(personID);
                        System.out.println("Person Deleted Successfuly!");
                    }else{
                        System.out.println("Such Person is Not Available!");
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }
        };

    }

    @Override
    public void attach(TransactionObserver observer) {
        observers.add(observer);

    }

    @Override
    public void detach(TransactionObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void Notify() throws IOException {
        for( TransactionObserver o:observers){
            o.update(this);
        }
    }

    @Override
    public Map<Material,String> getState() {
        return this.state;
    }

    @Override
    public void setState(String accountNumber, String transactionType, String amount ) {
        this.state.put(Material.ACCOUNTNUMBER,accountNumber);
        this.state.put(Material.TRANSACTIONTYPE,transactionType);
        this.state.put(Material.AMOUNT,amount);
    }

    public Bank returnSelf(){
        return this;
    }
}
