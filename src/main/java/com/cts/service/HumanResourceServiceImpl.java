package com.cts.service;


import java.time.LocalDate;

import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.model.Grades;

import com.cts.model.Employee;
import com.cts.model.GradesHistory;

import com.cts.repository.EmployeeRepo;
import com.cts.repository.GradesHistoryRepo;
import com.cts.repository.GradesRepo;




@Service

public class HumanResourceServiceImpl implements HumanResourceService{
	@Autowired
	EmployeeRepo employeeRepo;
	@Autowired
	GradesRepo gradesRepo;
	@Autowired
	private GradesHistoryRepo gradesHistoryRepo;
	
	private GradesHistory gHistory = new GradesHistory();
	
	public HumanResourceServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

	@Override
	public ResponseEntity<?> adduser(Employee employee)  {
        
		
		// Validate email address
	    if (!isValidEmailAddress(employee.getEmailAddress())) {
	        throw new InvalidEmailException("Invalid: " + employee.getEmailAddress());
	    }
	    
	    // Validate role
		if (!isValidRole(employee.getRole())) {
	        throw new InvalidRoleException("Invalid:" + employee.getRole());
	    }
   
        
        // Other validation logic
        Employee emp=new Employee();
		emp.getEmployeeId();
		emp.getFirstName();
		emp.getLastName();
		emp.getEmailAddress();
		emp.getPhoneNumber();
		emp.getRole();
		emp.getCurrentGradeId();
        
        employeeRepo.save(employee);
        return ResponseEntity.ok("Employee added successfully");
    }

	private boolean isValidEmailAddress(String email) {
	    String regex = "^[A-Za-z0-9+_.-]+@cognizant.com$";
	    return email.matches(regex);
	}
    private boolean isValidRole(String role) {
        return role.equalsIgnoreCase("Employee") || role.equalsIgnoreCase("HR") || role.equalsIgnoreCase("TravelDeskExe");
    }


		
	@Override
	public List<Employee> findallEmployee() {
		// TODO Auto-generated method stub
		return employeeRepo.findAll();
	}
	@Override
	public boolean deleteEmployeebyId(int EmployeeId) {
		// TODO Auto-generated method stub

		if(employeeRepo.findById(EmployeeId).isPresent()) {
			employeeRepo.deleteById(EmployeeId);
			return true;
		}else {
			return false;
		}
	}
	@Override
	public Employee updateEmployee(int EmployeeId, Employee employee) {
		// TODO Auto-generated method stub
		Employee empl=employeeRepo.findById(EmployeeId).orElseThrow(()-> new RuntimeException("Employee with Id "+EmployeeId+" does not exist"));
		empl.setEmployeeId(employee.getEmployeeId());
		empl.setFirstName(employee.getFirstName());
		empl.setLastName(employee.getLastName());
		empl.setPhoneNumber(employee.getPhoneNumber());
		empl.setEmailAddress(employee.getEmailAddress());
		empl.setRole(employee.getRole());
		empl.setCurrentGradeId(employee.getCurrentGradeId());
		employeeRepo.save(empl);
		return empl;
			
	}
	@Override
	public Employee getEmployeebyId(int EmployeeId) {
		// TODO Auto-generated method stub
		return employeeRepo.findById(EmployeeId).orElseThrow(()->new RuntimeException("Employee with Id "+EmployeeId+" does not exist"));
	}
	@Override
	public List<Grades> findallGrades() {
		// TODO Auto-generated method stub
		return gradesRepo.findAll();
	}
	@Override
	public boolean checkUser(int employeeId) {
		// TODO Auto-generated method stub
		if(employeeRepo.findById(employeeId).isPresent()) {
			return false;
		}else {
			return true;
		}
	}
	@Override
	public Employee updateEmployeeGrade(int employeeId, int gradeId) {
		// TODO Auto-generated method stub
		Employee empl=employeeRepo.findById(employeeId).orElseThrow(()-> new RuntimeException("Employee with Id "+employeeId+" does not exist"));
		empl.setCurrentGradeId(gradeId);
		employeeRepo.save(empl);
		LocalDate lt= LocalDate.now();
		LocalDate date = LocalDate.now();
		Date date3 = java.sql.Date.valueOf(date);
		Date date123 = new Date(System.currentTimeMillis());
//		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
//		System.out.println(formatter.format(date123));
		gHistory.setAssignedOn(date123);
		gHistory.setEmployeeId(employeeId);
		gHistory.setGradeId(gradeId);
		gradesHistoryRepo.save(gHistory);
		return empl;
	}
	
	

}