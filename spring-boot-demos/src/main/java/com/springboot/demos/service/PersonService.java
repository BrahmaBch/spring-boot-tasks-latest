package com.springboot.demos.service;

import java.util.List;

import com.springboot.demos.dto.Result;
import com.springboot.demos.model.Person;

public interface PersonService {
	
	Result savePerson(Person person);
	
	List<Person> getAllPersons();
}
