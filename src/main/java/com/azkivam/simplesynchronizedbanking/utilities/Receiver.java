package com.azkivam.simplesynchronizedbanking.utilities;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class Receiver {
    Scanner scanner ;

    public Receiver() {
        this.scanner = new Scanner(System.in);;
    }

    public Map<Material,String> receiveController(Map<Material,Boolean> control){
        Map<Material,String> result = new HashMap<>();

        if(control.get(Material.NAME)){
            System.out.println("Enter name: ");
//            String name = scanner.nextLine();
            String name = "kamran";
            result.put(Material.NAME,name);
        }
        if(control.get(Material.PERSONID)){
            System.out.println("Enter PERSONID: ");
        }
        if(control.get(Material.ACCOUNTNUMBER)){
            System.out.println("Enter ACCOUNTNUMBER: ");
        }
        if(control.get(Material.INITIALAMOUNT)){
            System.out.println("Enter INITIALAMOUNT: ");
        }

        if(control.get(Material.AMOUNT)){
            System.out.println("Enter AMOUNT: ");
        }
        if(control.get(Material.SOURCEACCOUNTNUMBER)){
            System.out.println("Enter SOURCEACCOUNTNUMBER: ");
        }
        if(control.get(Material.DESTINATIONACCOUNTNUMBER)){
            System.out.println("Enter DESTINATIONACCOUNTNUMBER: ");
        }

        return result;
            }




}
