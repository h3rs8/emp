package com.assignment.assignment.Repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.assignment.assignment.Models.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, UUID>{
	
}
