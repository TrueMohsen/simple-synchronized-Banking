package com.azkivam.simplesynchronizedbanking.services;

import com.azkivam.simplesynchronizedbanking.entities.Person;

import java.util.List;

public interface PersonService {

    boolean exists(Person person);
    boolean exists(Long personId);
    Person create(Person person);
    Person update(Person person);

    Person get(Long personId);
    List<Person> getAll();
}
