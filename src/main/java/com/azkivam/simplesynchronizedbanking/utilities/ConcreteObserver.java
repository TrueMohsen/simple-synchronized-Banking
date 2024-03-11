package com.azkivam.simplesynchronizedbanking.utilities;

import com.azkivam.simplesynchronizedbanking.controllers.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConcreteObserver implements TransactionObserver{

    Subject subject;
    Map<Material,String> state = new HashMap<>();

    public ConcreteObserver() {
        //inja
        subject = new Bank().returnSelf();
        state =  this.subject.getState();
    }

    @Override
    public void onTransaction(String accountNumber, String transactionType, String amount) throws IOException {
//        print to file
        FileWriter fileWriter = new FileWriter("M:\\master\\azkivam\\logger.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(accountNumber);
        printWriter.print(transactionType);
        printWriter.printf(amount);
//        printWriter.printf(accountNumber);
        printWriter.close();
    }

    @Override
    public void update(Subject s) throws IOException {
        subject=s;
        onTransaction(subject.getState().get(Material.ACCOUNTNUMBER) ,subject.getState().get(Material.TRANSACTIONTYPE),subject.getState().get(Material.AMOUNT));
    }



}
