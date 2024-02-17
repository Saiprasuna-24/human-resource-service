package com.cts.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="grades")
@Getter
@Setter
@ToString
@NoArgsConstructor
//@ApiModel(value="grades")
public class Grades {
//	@ApiModelProperty(notes="id")
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	@ApiModelProperty(notes="Name is mandatory")
	private String name;

}
