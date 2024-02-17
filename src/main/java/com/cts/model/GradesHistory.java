package com.cts.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="gradeshistory")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GradesHistory {
	@NotNull(message="Id is mandatory")
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	private Date AssignedOn;
	@NotNull(message="EmployeeId id mandatory")
	private int employeeId;
	@NotNull
	private int gradeId;
}
