package com.cts.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.service.HumanResourceService.ErrorResponse;
import com.cts.service.HumanResourceService.InvalidEmailException;
//import com.cts.service.HumanResourceService.InvalidEmailException;
import com.cts.service.HumanResourceService.InvalidRoleException;
@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    private Object data;
	@ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ErrorResponse> handleInvalidationException(InvalidRoleException ex) {
    	ErrorResponse errorResponse = new ErrorResponse(false, HttpStatus.BAD_REQUEST.value(), "Invalid role: " + ex.getMessage(), data);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
   }
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ErrorResponse> handleInvalidationException(InvalidEmailException ex) {
    	ErrorResponse errorResponse = new ErrorResponse(false, HttpStatus.BAD_REQUEST.value(), "Invalid Email: " + ex.getMessage(), data);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
   }




}