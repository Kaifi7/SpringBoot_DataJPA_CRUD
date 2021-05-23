package com.luv2code.springboot.cruddemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="employee")
@ApiModel(description ="All details about the employee")
public class Employee {
	
	//define fields
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	@ApiModelProperty(notes = "The database generated employee ID")
	@NotNull(message = "Primary key cannot be null")
	private int id;
	
	@Column(name="first_name")
	@ApiModelProperty(notes = "The employee First Name")
	@NotNull(message = "First Name of employee cannot be null")
	@Size(max = 100)
	@NotBlank
	private String firstName;
	
	@Column(name="last_name")
	@ApiModelProperty(notes = "The employee Last Name")
	private String lastName;
	
	@Column(name="email")
	@ApiModelProperty(notes = "The employee Email")
	@NotNull(message = "Email cannot be null")
	private String email;
	
	// define constructors
	public Employee() {
		
	}

	public Employee(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	// define getter/setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	//define tostring

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
	
	
}
