package com.azkivam.simplesynchronizedbanking.services;

import com.azkivam.simplesynchronizedbanking.entities.Person;
import com.azkivam.simplesynchronizedbanking.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public boolean exists(Person person) {
        return personRepository.existsById(person.getId());
    }

    @Override
    public Person create(Person person) {
        personRepository.save(person);
        return person;
    }

    @Override
    public Person update(Person person) {
        personRepository.save(person);
        return person;
    }
}
