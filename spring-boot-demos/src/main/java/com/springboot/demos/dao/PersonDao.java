package com.springboot.demos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demos.model.Person;


@Repository
public interface PersonDao extends JpaRepository<Person, Long> {

}
