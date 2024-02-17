package com.cts.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")

public class Employee {
	
	
    @Id
    @SequenceGenerator(name="hrseq",initialValue=100000,allocationSize=1,sequenceName="HR_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="hrseq") 
  
	private int employeeId;
	
    @NotBlank(message = "FirstName is required")
	private String firstName;
	
   // @NotBlank(message = "LastName is Required")
	private String lastName;
	
    //@NotBlank(message = "PhoneNumber is Required")
    //@Size(min = 10,max=10,message= "Phone Number should consists of 10 digits" )
    
	private String phoneNumber;

    //@NotBlank(message = "EmailAddress is Required")
 //@Pattern(regexp = "[a-zA-Z0-9._%+-]+@cognizant\\.com", message = "Email address should be in the format 'abc@cognizant.com'.")
   // @Pattern(regexp = ".+@cognizant\\.com$", message = "Email address must end with @cognizant.com")
	private String emailAddress;
	

    //@Pattern(regexp = "Employee|HR|TravelDeskExe", message = "Select any one from the list - Employee, HR,TravelDeskExe ")
    
    private String role;
	
	private int currentGradeId;
	
	
	 private boolean accessGranted = true;
	
}
