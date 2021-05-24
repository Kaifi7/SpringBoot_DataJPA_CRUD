package com.luv2code.springboot.cruddemo.rest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springboot.cruddemo.dao.EmployeeRepository;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import static org.mockito.Mockito.when;

class EmployeeRestControllerTest {
	private MockMvc mvc;

	@Mock
	private EmployeeService employeeService;
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private EmployeeRestController employeeRestController;
	
	@BeforeEach()
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(employeeRestController).build();
	}
	
	@Test
	public void testGetAllEmployees() throws Exception {
		Employee emp1 = new Employee();
		emp1.setId(1);
		emp1.setFirstName("fist");
		emp1.setLastName("Name");
		emp1.setEmail("first@email.com");
		
		Employee emp2 = new Employee();
		emp2.setId(2);
		emp2.setFirstName("Second");
		emp2.setLastName("Name");
		emp2.setEmail("second@email.com");
		
		List<Employee>employeeList = new ArrayList<>();
		List<Employee>result = new ArrayList<>();
		employeeList.add(emp1);
		employeeList.add(emp2);
		
		when(employeeService.findAll()).thenReturn(employeeList);
		result = employeeRestController.findAll();
		Assertions.assertNotNull(result);
		
		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(employeeList);
		MvcResult rs = mvc.perform(MockMvcRequestBuilders.get("/api/employees").accept(MediaType.APPLICATION_JSON_VALUE)
							.content(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
							.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Assertions.assertEquals(200, rs.getResponse().getStatus());
		
		String outJson = rs.getResponse().getContentAsString();
		Assertions.assertEquals(outJson, inputJson);
		
	}
	
	@Test
	public void testGetEmployeeById() throws Exception {
		Employee employee = new Employee();
		employee.setId(1);
		employee.setFirstName("Sultan_Mehmed");
		employee.setLastName("Al_Fateh");
		employee.setEmail("mehmed121@gmail.com");
		
		when(employeeService.findById(1)).thenReturn(employee);
		Employee emp = employeeRestController.getEmployee(1);
		Assertions.assertEquals(emp, employee);
		
		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(employee);
		MvcResult rs = mvc.perform(MockMvcRequestBuilders.get("/api/employees?ID=1").accept(MediaType.APPLICATION_JSON_VALUE)
							.content(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
							.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Assertions.assertEquals(200, rs.getResponse().getStatus());
		
		String outJson = rs.getResponse().getContentAsString();
		//Assertions.assertEquals(outJson, jsonAsString);
	}
	
	@Test
	public void testGetEmployeeByIdThrowsException() throws Exception{
		Employee employee = new Employee();
		employee = null;
		
		when(employeeService.findById(1)).thenThrow(RuntimeException.class);
		MvcResult rs = mvc.perform(MockMvcRequestBuilders.get("/api/employees?ID=1")).andReturn();
		Assertions.assertEquals(200, rs.getResponse().getStatus());
		
	}
	
	@Test
	public void testAddEmployee() throws Exception {
		Employee employee = new Employee();
		//employee.setId(10);
		employee.setFirstName("Salahdin");
		employee.setLastName("Ayyubi");
		employee.setEmail("salahdin@gmail.com");
		
		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(employee);
		
		Mockito.when(employeeService.save(Mockito.any(Employee.class))).thenReturn(employee);
		System.out.println(inputJson);
		System.out.println("----------");
		//Mockito.doNothing().when(employeeService).save(Mockito.any(Employee.class));
		Employee res = employeeRestController.addEmployee(employee);
		MvcResult rs = mvc.perform(MockMvcRequestBuilders.post("/api/employees").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Assertions.assertEquals(200, rs.getResponse().getStatus());
		String outJson = rs.getResponse().getContentAsString();
		Assertions.assertEquals(outJson, inputJson);
		Assertions.assertEquals(res.getFirstName(), employee.getFirstName());
		
	}
	
	@Test
	public void testUpdateEmployee() throws Exception{
		Employee employee = new Employee();
		employee.setId(2);
		employee.setFirstName("Salahdin");
		employee.setLastName("Ayyubi");
		employee.setEmail("salahdin@gmail.com");
		
		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(employee);
		
		Mockito.when(employeeService.save(Mockito.any(Employee.class))).thenReturn(employee);
		
		MvcResult rs = mvc.perform(MockMvcRequestBuilders.put("/api/employees").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Assertions.assertEquals(200, rs.getResponse().getStatus());
	}
	
	
	/*
	 * @Test public void testDeleteEmployee() throws Exception { Employee employee =
	 * new Employee(); employee.setId(2); employee.setFirstName("Salahdin");
	 * employee.setLastName("Ayyubi"); employee.setEmail("salahdin@gmail.com");
	 * 
	 * Mockito.when(employeeService.deleteById(2)).thenReturn("SUCCESS");
	 * //Mockito.doNothing().when(employeeService).deleteById(2); ObjectMapper
	 * mapper = new ObjectMapper(); String jsonAsString =
	 * mapper.writeValueAsString(employee); MvcResult rs =
	 * mvc.perform(MockMvcRequestBuilders.delete("/api/employees?employeeId=2"))
	 * .andReturn(); Assertions.assertEquals(200, rs.getResponse().getStatus());
	 * 
	 * }
	 */

}
