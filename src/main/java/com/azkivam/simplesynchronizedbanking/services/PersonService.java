package com.azkivam.simplesynchronizedbanking.services;

import com.azkivam.simplesynchronizedbanking.entities.Person;

public interface PersonService {

    boolean exists(Person person);
    Person create(Person person);
    Person update(Person person);
}
