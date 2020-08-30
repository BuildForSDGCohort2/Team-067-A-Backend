package com.sdgcrm.application.repository;

import com.sdgcrm.application.data.entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}