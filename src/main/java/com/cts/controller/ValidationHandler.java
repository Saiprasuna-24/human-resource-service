package com.cts.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice//it provides centralized exception handling for controllers. 
public class ValidationHandler extends ResponseEntityExceptionHandler {
//It extends the ResponseEntityExceptionHandler class which is provided by Spring to handle exceptions thrown from controllers.
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//The handleMethodArgumentNotValid method is an overridden method from the ResponseEntityExceptionHandler class, which handles the exceptions thrown 
// by validation annotations in the request body. When such an exception is thrown, this method is called to handle the exception.			
			HttpHeaders headers, HttpStatus status, WebRequest request) {
// In this method, a Map object named errors is created to store the error messages, and the getAllErrors method of the BindingResult object is used to 
		//iterate over all the errors.
		Map<String, String> errors = new HashMap<>();
//Each error is then added to the errors map with the field name and the error message. 
		ex.getBindingResult().getAllErrors().forEach((error) ->{
			
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
//Finally, a new ResponseEntity object is created with the errors map and the BAD_REQUEST HTTP status, which is returned to the client as a response.
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
		
	}
	
}