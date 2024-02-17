package com.cts.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cts.model.Employee;
import com.cts.model.Grades;

public interface HumanResourceService {
	
	public ResponseEntity<?> adduser(Employee employee) ;
	List<Employee> findallEmployee();
	public boolean deleteEmployeebyId(int EmployeeId);
	public Employee updateEmployee(int EmployeeId, Employee employee);
	public Employee getEmployeebyId(int EmployeeId);
	public List<Grades> findallGrades();
	public boolean checkUser(int employeeId);
	public Employee updateEmployeeGrade(int employeeId, int gradeId);
	
	
	
	public class InvalidRoleException extends RuntimeException {
	    public InvalidRoleException(String message) {
	        super(message="Role should consists of HR, Employee and TravelDeskExe");
	    }}
	public class InvalidEmailException extends RuntimeException {
	    public InvalidEmailException(String message) {
	        super(message="Email Address should be the format xxx@cognizant.com");
	    }}
	
	public class ErrorResponse {
	// private boolean success;	
    private int status;
    private String message;
   
    public ErrorResponse(boolean success,int status, String employee,Object data) {
    	
    	this.status = status;
        this.message = employee;
       
    }

	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
}
