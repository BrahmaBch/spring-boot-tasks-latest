package com.springboot.demos.model;


import java.time.LocalDateTime;

import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "person", schema = "public")
@DynamicUpdate
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Person {

	@Id
	@Column(name ="person_id")
    private Long personId;
	@Column(name ="person_name")
    private String personName;
	@Column(name ="person_salary")
    private Double personSalary;
	@Column(name ="email")
    private String email;
	@Column(name ="password")
    private String password;
	@Column(name ="joining_date")
    private LocalDateTime joiningDate;
    
}
