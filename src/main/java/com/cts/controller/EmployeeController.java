package com.cts.controller;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.model.AuthenticationRequest;
import com.cts.model.AuthenticationResponse;
import com.cts.model.Employee;
import com.cts.model.Grades;
import com.cts.repository.EmployeeRepo;
import com.cts.repository.GradesRepo;
import com.cts.service.HumanResourceService;
import com.cts.service.HumanResourceService.ErrorResponse;
import com.cts.service.MyUserDetailService;
import com.cts.util.JwtUtil;

@CrossOrigin("*")
@RestController
public class EmployeeController {
	@Autowired
	private HumanResourceService humanResourceService;
	@Autowired
	EmployeeRepo employeeRepository;
	GradesRepo gradeRepository;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MyUserDetailService service;
	
	@PostMapping("api/authenticate")
	public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest req) throws Exception
	{
		try
		{
			authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword()));
		}
		catch(Exception e)
		{
			throw new Exception("Incorrect Username and Password",e);
		}
		final UserDetails userDetails=service.loadUserByUsername(req.getUsername());
		final String jwt=jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	@GetMapping("/api/employees")
	public ResponseEntity<List<Employee>> findallEmployee(){
	   // List<Employee> users = humanResourceService.findallEmployee();
	    return new ResponseEntity<List<Employee>>(employeeRepository.findAll(),HttpStatus.OK);
	}

	

	/*@PostMapping(value = "/api/employees",produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> adduser(@RequestBody Employee employee)throws Exception {
	    humanResourceService.adduser(employee);
	   // return new ResponseEntity<String>("Employee with id " + employee.getEmployeeId() + " has been created.", HttpStatus.CREATED);
	    return new ResponseEntity<String>("Employee with id " + employee.getEmployeeId() + " has been created.",HttpStatus.CREATED);
	}*/
	@PostMapping(value = "/api/employees", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> adduser(@RequestBody Employee employee) throws Exception {
	    humanResourceService.adduser(employee);
	    return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}

	@GetMapping(value = "/api/employees/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEmployeeById(@PathVariable int employeeId) {
	    Optional<Employee> emp = employeeRepository.findById(employeeId);
	    if (emp.isPresent()) {
	        return ResponseEntity.ok(emp.get());
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with id " + employeeId + " not found.");
	    }
	}




	/*@PutMapping(value = "/api/employees/{employeeId}", produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateEmployee(@PathVariable int employeeId, @RequestBody Employee employee){
	    //return new ResponseEntity<Employee>(humanResourceService.updateEmployee(employeeId, employee), HttpStatus.OK);
		
		    Optional<Employee> employeeData = employeeRepository.findById(employeeId);// Find the user with the given id in the repository

		    if (employeeData.isPresent()) {// If the user is found in the repository
		        Employee _emp = employeeData.get();// Get the found user
		        _emp.setFirstName(employee.getFirstName());// Update the name of the found user
		        _emp.setLastName(employee.getLastName()); // Update the role of the found user
		        _emp.setPhoneNumber(employee.getPhoneNumber()); // Update the email of the found user
		         _emp.setEmailAddress(employee.getEmailAddress());// Update the active status of the found user
		         _emp.setRole(employee.getRole());
		         _emp.setCurrentGradeId(employee.getCurrentGradeId());
		         employeeRepository.save(_emp);// Save the updated user to the repository
		     // Create a response entity with a success message and HTTP status code 200 OK
		        return new ResponseEntity<>("Employee with id " + employeeId + " has been updated.", HttpStatus.OK);
		    } else {// If the user is not found in the repository
		        // Create a response entity with an error message and HTTP status code 404 NOT FOUND
		        return new ResponseEntity<>("Employee with id " + employeeId + " not found.", HttpStatus.NOT_FOUND);
		    }
		}*/
	
	@PutMapping(value = "/api/employees/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> updateEmployee(@PathVariable int employeeId, @RequestBody Employee employee) {

	    Optional<Employee> employeeData = employeeRepository.findById(employeeId); // Find the user with the given id in the repository

	    if (employeeData.isPresent()) { // If the user is found in the repository
	        Employee _emp = employeeData.get(); // Get the found user
	        _emp.setFirstName(employee.getFirstName()); // Update the name of the found user
	        _emp.setLastName(employee.getLastName()); // Update the role of the found user
	        _emp.setPhoneNumber(employee.getPhoneNumber()); // Update the email of the found user
	        _emp.setEmailAddress(employee.getEmailAddress()); // Update the active status of the found user
	        _emp.setRole(employee.getRole());
	        _emp.setCurrentGradeId(employee.getCurrentGradeId());
	        Employee updatedEmployee = employeeRepository.save(_emp); // Save the updated user to the repository
	        // Create a response entity with the updated user and HTTP status code 200 OK
	        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
	    } else { // If the user is not found in the repository
	        // Create a response entity with an error message and HTTP status code 404 NOT FOUND
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

		

	
//	@DeleteMapping(value ="/api/employees/{employeeId}", produces =  MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> deleteEmployee(@PathVariable int employeeId) {
//		if(humanResourceService.deleteEmployeebyId(employeeId)) {
//			return new ResponseEntity<>("User with id " + employeeId + " has been deleted.", HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>("User with id " + employeeId + " not found.", HttpStatus.NOT_FOUND);
//		}
//	}
	@DeleteMapping(value ="/api/employees/{employeeId}", produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteEmployee(@PathVariable int employeeId) {
	    if(humanResourceService.deleteEmployeebyId(employeeId)) {
	        return new ResponseEntity<>(HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}


	@GetMapping("/api/grades")
	public ResponseEntity<List<Grades>> findallGrades() {
	    List<Grades> grades = humanResourceService.findallGrades();
	    return new ResponseEntity<List<Grades>>(grades, HttpStatus.OK);
	}

	@PutMapping("/api/employees/{employeeId}/{gradeId}")
	public ResponseEntity<?> updateEmployeeGrade(@PathVariable int employeeId, @PathVariable int gradeId) {
	    Employee updatedEmployee = humanResourceService.updateEmployeeGrade(employeeId, gradeId);
	    if (updatedEmployee != null) {
	        return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<String>("Employee or Grade not found", HttpStatus.NOT_FOUND);
	    }
	    
	}
}
