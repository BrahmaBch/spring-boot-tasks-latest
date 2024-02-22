package com.springboot.demos.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demos.dto.Result;
import com.springboot.demos.model.Person;
import com.springboot.demos.service.PersonService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/person")
@Slf4j
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping("/addPerson")
	public ResponseEntity<Result> addPerson(@RequestBody Person person) {
		Result result = personService.savePerson(person);
		log.info("Person sucessfully saved.");
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/savePersons")
    public ResponseEntity<Result> savePersons(@RequestBody List<Person> persons) {
        persons.stream().forEach(person -> {
            Result result = personService.savePerson(person);
            log.info("Person successfully saved: {}", person.getPersonName());
        });
        Result result = new Result();
        result.setStatusCode(HttpStatus.OK.value());
        result.setSuccesMessage("All persons successfully saved");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
	
	@GetMapping("/getAllPersons")
    public ResponseEntity<List<Person>> getAllPersons() {
		List<Person> persons = personService.getAllPersons(); // Retrieve all persons from the database
        return new ResponseEntity<>(persons, HttpStatus.OK); // Return the list of persons
    }
	
	@GetMapping("/bySalaryRange")
    public ResponseEntity<List<Person>> getPersonsBySalaryRange(@RequestParam("minSalary") Double minSalary,
            @RequestParam("maxSalary") Double maxSalary) {
		// get all persons
		List<Person> persons = personService.getAllPersons();
        // Filter persons based on the salary range
		List<Person> filteredPersons = persons.stream()
				.filter(person -> person.getPersonSalary().compareTo(minSalary) >= 0
						&& person.getPersonSalary().compareTo(maxSalary) <= 0)
				.collect(Collectors.toList());
        return new ResponseEntity<>(filteredPersons, HttpStatus.OK);
    }
	
	@GetMapping("/bySalary")
	public ResponseEntity<List<Person>> getPersonsBySalaryRange(@RequestParam("salary") Double salary) {
		// get all persons
		List<Person> persons = personService.getAllPersons();
		List<Person> filteredPersons = persons.stream()
				.filter(person -> person.getPersonSalary().equals(salary))
				.collect(Collectors.toList());
		return new ResponseEntity<>(filteredPersons, HttpStatus.OK);
	}
	
}
