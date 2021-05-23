package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@Api(value = "Employee Management System")
public class EmployeeRestController {
	
	
	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	
	//expose "/employees" and return list of employees
	@ApiOperation(value = "View a list of available employees", response = Employee.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("/employees")
	public List<Employee>findAll(){
		return employeeService.findAll();
	}
	
	//add mapping for GET/employee/{employeeId}
	@ApiOperation(value = "Get Employee by Id", response = Employee.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("/employees/{ID}")
	public Employee getEmployee(
			@ApiParam(value = "Employee id from which employee object will retrieve", required = true)
			@PathVariable(value = "ID") int employeeId) {
		Employee theEmployee =  employeeService.findById(employeeId);
		if(theEmployee == null) {
			throw new RuntimeException("Employee id not found: " +employeeId);
		}
		return theEmployee;
	}
	
	//add mapping for POST/employees - add new employee
	@PostMapping("/employees")
	@ApiOperation(value = "Add a new Employee")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	public Employee addEmployee(
			@ApiParam(value = "Employee object store it in a database table", required = true)
			@RequestBody Employee theEmployee) {
		
		// also jst in case they pass an id in JSON ... set id to 0
		//this is to force a save of new item ... instead of update
		theEmployee.setId(0);
		
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	//add mapping for PUT/employees - update existing employee
	@PutMapping("/employees")
	@ApiOperation(value = "Update the employee")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	public Employee updateEmployee(
			@ApiParam(value = "Update this Employee object")
			@RequestBody Employee theEmployee) {
		employeeService.save(theEmployee);
		return theEmployee;
	}
	
	//add mapping for DELETE/employees - delete employee
	@DeleteMapping("/employees/{employeeId}")
	@ApiOperation("Delete Employee")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	public String deleteEmployee(
			@ApiParam(value = "Employee id based on which the employee will be deleted")
			@PathVariable int employeeId) {
		
		Employee tempEmployee = employeeService.findById(employeeId);
		
		//throw exception if null
		if(tempEmployee == null) {
			throw new RuntimeException("Employee id not found - " +employeeId);
		}
		employeeService.deleteById(employeeId);
		return "Deleted employee id - " +employeeId;
		
	}
}
