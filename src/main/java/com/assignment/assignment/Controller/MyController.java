package com.assignment.assignment.Controller;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.assignment.Models.Employee;
import com.assignment.assignment.Repository.EmployeeRepository;
import com.assignment.assignment.Service.EmailService;

@RestController
@RequestMapping("/Employee")
public class MyController {
	@Autowired
	EmailService email;
	@Autowired
	private EmployeeRepository EmployeeRepository;
	private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public static boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
	@PostMapping("/")
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
		
		if(employee.getPhoneNumber().trim().length()!=10 || validateEmail(employee.getEmail()))
			return new ResponseEntity<Employee>(HttpStatus.PRECONDITION_FAILED);
		Employee u = new Employee();
		u.setEmail(employee.getEmail());
		u.setEmployeeName(employee.getEmployeeName());
		u.setPhoneNumber(employee.getPhoneNumber());
		u.setProfileImage(employee.getProfileImage());
		u.setReportsTo(employee.getReportsTo());
		u = EmployeeRepository.save(u);
		if(u.getId()!=null) {
			if(u.getReportsTo()!=null)
				email.sendEmail(u);
			
			return ResponseEntity.ok(u.getId());
		}
		
		else
			return new ResponseEntity<Employee>(HttpStatus.NOT_IMPLEMENTED);
		
	}
	@GetMapping("/getall/{size}/{page}/{sortby}")
	public ResponseEntity<?> getallEmployees(@PathVariable("size") int size,@PathVariable("page") int page,@PathVariable("sortby") String sortby){
		Sort sort = Sort.by(sortby);
		Pageable pageable = 
				  PageRequest.of(page,size , Sort.by(sortby));

		return ResponseEntity.ok(this.EmployeeRepository.findAll(pageable).get());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployee(@PathVariable("id") UUID id){
		Employee u = this.EmployeeRepository.findById(id).orElse(null);
		if(u==null)
			return new ResponseEntity<Employee>(u,HttpStatus.NOT_FOUND);
		else 
			return ResponseEntity.ok(u);
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable("id") UUID id){
		this.EmployeeRepository.deleteById(id);		
				
	}
	@PutMapping("/")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee Employee) {
		Employee u = EmployeeRepository.findById(Employee.getId()).orElse(null);
		if(u==null)
			return new ResponseEntity<Employee>(u,HttpStatus.NOT_MODIFIED);
			
		u.setEmail(Employee.getEmail());
		u.setEmployeeName(Employee.getEmployeeName());
		u.setPhoneNumber(Employee.getPhoneNumber());
		u.setProfileImage(Employee.getProfileImage());
		u.setReportsTo(Employee.getReportsTo());
		u = EmployeeRepository.save(u);
		if(u.getId()!=null) {
			
			return ResponseEntity.ok(u.getId());
		}
		else
			return new ResponseEntity<Employee>(u,HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/{id}/{level}")
	public ResponseEntity<?> getLevel(@PathVariable("id") UUID id, @PathVariable("level") Integer level){
		
		Employee u = EmployeeRepository.findById(id).get();
		if(u==null)
			return new ResponseEntity<Employee>(u,HttpStatus.NOT_FOUND);
		for(int i = 0;i<level;i++) {
			// If the reportsTo key is null.
			if(u.getReportsTo()==null)
					return new ResponseEntity<Employee>(u,HttpStatus.NOT_FOUND);
			
			
			u = EmployeeRepository.findById(u.getReportsTo()).orElseGet(null);
			
			if(u==null)
				return new ResponseEntity<Employee>(u,HttpStatus.NOT_FOUND);
					
		}
		return new ResponseEntity<Employee>(u,HttpStatus.OK);
		
	}
	
	
	

}
