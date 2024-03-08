package com.azkivam.simplesynchronizedbanking.services;

import com.azkivam.simplesynchronizedbanking.entities.Person;
import com.azkivam.simplesynchronizedbanking.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public boolean exists(Person person) {
        return personRepository.existsById(person.getId());
    }

    @Override
    public boolean exists(Long personId) {
        return personRepository.existsById(personId);
    }

//    @Override
//    public boolean exists(String personName) {
//        return personRepository.exists(personName);
//    }

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

    @Override
    public Optional<Person> get(Long personId) {
        return personRepository.findById(personId);
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

}
