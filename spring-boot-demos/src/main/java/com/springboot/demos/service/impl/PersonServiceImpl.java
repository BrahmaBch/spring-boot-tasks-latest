package com.springboot.demos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.demos.dao.PersonDao;
import com.springboot.demos.dto.Result;
import com.springboot.demos.model.Person;
import com.springboot.demos.service.PersonService;

@Service("personService")
public class PersonServiceImpl implements PersonService{

	@Autowired
	PersonDao personDao;
	
	@Override
	public Result savePerson(Person person) {
		personDao.save(person);
		Result result = new Result();
		result.setSuccesMessage("Person Saved");
		result.setStatusCode(HttpStatus.OK.value());
		return result;
	}

	@Override
	public List<Person> getAllPersons() {
		Result result = new Result();
		List<Person> persons = personDao.findAll();
		result.setSuccesMessage("All Persons Fetched");
		result.setStatusCode(HttpStatus.OK.value());
		return persons;
	}

	
}
