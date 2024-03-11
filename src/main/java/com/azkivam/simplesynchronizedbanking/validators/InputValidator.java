package com.azkivam.simplesynchronizedbanking.validators;

import org.springframework.stereotype.Component;

@Component
public class InputValidator {

    public Boolean checkNameLength(String name){
        return name.length() > 2 && name.length() < 12;
    }
}
