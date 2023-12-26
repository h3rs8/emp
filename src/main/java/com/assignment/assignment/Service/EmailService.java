package com.assignment.assignment.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.assignment.assignment.Models.Employee;
import com.assignment.assignment.Repository.EmployeeRepository;
@Service
public class EmailService {
	
	 @Autowired
	    private JavaMailSender javaMailSender;
	 @Autowired
	 EmployeeRepository empRepository;

	    public void sendEmail(Employee emp) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        Employee Manager = empRepository.findById(emp.getReportsTo()).orElse(null);
	        if(Manager!=null && Manager.getEmail()!=null) {
	        	message.setTo(Manager.getEmail());
		        message.setSubject("Regarding Employee now working under you");
		        message.setText(emp.getEmployeeName()+" will now work under you. Mobile number is "+ emp.getPhoneNumber()+" and email is "+emp.getEmail());
	        	
	        	
		        javaMailSender.send(message);
		        System.out.println("Message Sent");
		        
	        }
	        
	    }

}
