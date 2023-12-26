package com.assignment.assignment.Models;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "employees")
public class Employee {
	
	private UUID id;
	private String EmployeeName;
	private Long PhoneNumber;
	private String Email;
	private String ProfileImage;
	private UUID ReportsTo;

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getEmployeeName() {
		return EmployeeName;
	}
	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}
	public Long getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getProfileImage() {
		return ProfileImage;
	}
	public void setProfileImage(String profileImage) {
		ProfileImage = profileImage;
	}
	
	public UUID getReportsTo() {
		return ReportsTo;
	}
	public void setReportsTo(UUID reportsTo) {
		ReportsTo = reportsTo;
	}
	public Employee(UUID id, String employeeName, Long phoneNumber, String email, String profileImage, UUID reportsTo) {
		super();
		this.id = id;
		EmployeeName = employeeName;
		PhoneNumber = phoneNumber;
		Email = email;
		ProfileImage = profileImage;
		ReportsTo = reportsTo;
	}
	public Employee() {
		super();
		id=UUID.randomUUID();
		System.out.println("id assigned");
	}	

	
}
