package com.azkivam.simplesynchronizedbanking.services;

import com.azkivam.simplesynchronizedbanking.entities.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    boolean exists(Person person);
    boolean exists(Long personId);
    Person create(Person person);
    Person update(Person person);

    Optional<Person> get(Long personId);
    List<Person> getAll();
}
