package com.cts;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.cts.controller.EmployeeController;
import com.cts.model.Employee;
import com.cts.model.Grades;
import com.cts.repository.EmployeeRepo;
import com.cts.repository.GradesRepo;
import com.cts.service.HumanResourceService;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Assert;
	@SpringBootTest
	public class HumanResourceServiceApplicationTests {

	    @InjectMocks
	    private EmployeeController employeeController;

	    @Mock
	    private HumanResourceService humanResourceService;

	    @Mock
	    private EmployeeRepo employeeRepository;

	    @Mock
	    private GradesRepo gradeRepository;
	    ObjectMapper objectMapper = new ObjectMapper();

		private Employee Employee;
	    @BeforeEach
	    public void setUp() throws Exception {
	        MockitoAnnotations.initMocks(this);
	    }
	    
	    @Test
	    public void testFindallEmployee() throws Exception {
	        List<Employee> employeeList = new ArrayList<>();
	        employeeList.add(new Employee(900000, "Manoj", "D", "manoj@cogniznt.com", "8712312939", "Employee", 1, false));

	        when(employeeRepository.findAll()).thenReturn(employeeList);

	        ResponseEntity<List<Employee>> responseEntity = employeeController.findallEmployee();
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(employeeList, responseEntity.getBody());

	        verify(employeeRepository).findAll();
	    }
	    @Test
	    public void testAddEmployee() throws Exception {
	        Employee emp = new Employee(900000, "Manoj", "D", "manoj@cogniznt.com", "8712312939", "Employee", 1, false);
	        String jsonRequest = objectMapper.writeValueAsString(emp);

	        when(employeeRepository.save(any(Employee.class))).thenReturn(emp);
	        ResponseEntity<Employee> responseEntity = employeeController.adduser(emp);
	        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	        assertNotNull(responseEntity.getBody());
	        assertEquals(emp.getFirstName(), responseEntity.getBody().getFirstName());
	        assertEquals(emp.getLastName(), responseEntity.getBody().getLastName());
	    }


	    @Test
	    public void testGetEmployeeById() throws Exception {
	        Employee employee = new Employee(900000, "Manoj", "D", "manoj@cogniznt.com", "8712312939", "Employee", 1, false);

	        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));

	        ResponseEntity<?> responseEntity = employeeController.getEmployeeById(1);
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(employee, responseEntity.getBody());

	        verify(employeeRepository).findById(1);
	    }
	    @Test
	    public void testGetEmployeeById_NotFound() throws Exception {
	        int employeeId = 100000;
	        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

	        ResponseEntity<?> responseEntity = employeeController.getEmployeeById(employeeId);

	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	        assertNotNull(responseEntity.getBody());
	        assertEquals("Employee with id " + employeeId + " not found.", responseEntity.getBody().toString());

	        verify(employeeRepository).findById(employeeId);
	    }


	    @Test
	    public void testUpdateEmployee() throws Exception {
	        Employee employee = new Employee(1, "Manoj", "A", "manoja@cogniznt.com", "8712312939", "Employee", 2, false);

	        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
	        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

	        ResponseEntity<?> responseEntity = employeeController.updateEmployee(1, employee);
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals("Employee with id 1 has been updated.", responseEntity.getBody());

	        verify(employeeRepository).findById(1);
	        verify(employeeRepository).save(employee);
	    }
	    
	    @Test
	    public void testUpdateEmployee_NotFound() throws Exception {
	        int employeeId = 1;
	        Employee employee = new Employee(1, "Sai", "A", "sai@cognizant.com", "8712331234", "Manager", 2, true);
	        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

	        ResponseEntity<?> responseEntity = employeeController.updateEmployee(employeeId, employee);

	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	        assertNotNull(responseEntity.getBody());
	        assertEquals("Employee with id " + employeeId + " not found.", responseEntity.getBody().toString());

	        verify(employeeRepository).findById(employeeId);
	        verify(employeeRepository, never()).save(any(Employee.class));
	    }

	

	 @Test
	    public void testDeleteEmployeeSuccess() {
	        int employeeId = 100000;
	        Mockito.when(humanResourceService.deleteEmployeebyId(employeeId)).thenReturn(true);
	        ResponseEntity<?> response = employeeController.deleteEmployee(employeeId);
	        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	        Assert.assertEquals("User with id " + employeeId + " has been deleted.", response.getBody());
	    }
	    
	    @Test
	    public void testDeleteEmployeeNotFound() {
	        int employeeId = 100000;
	        Mockito.when(humanResourceService.deleteEmployeebyId(employeeId)).thenReturn(false);
	        ResponseEntity<?> response = employeeController.deleteEmployee(employeeId);
	        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        Assert.assertEquals("User with id " + employeeId + " not found.", response.getBody());
	    }}