package com.sdgcrm.application.data.service;

import com.sdgcrm.application.data.entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}