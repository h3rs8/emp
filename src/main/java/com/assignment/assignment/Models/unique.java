package com.assignment.assignment.Models;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


public class unique {	
	@Id
	private UUID id;

	public unique(UUID id) {
		super();
		this.id = id;
	}
	
	

}
