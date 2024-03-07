package com.azkivam.simplesynchronizedbanking.repositories;

import com.azkivam.simplesynchronizedbanking.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
