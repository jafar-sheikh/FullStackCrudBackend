package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourseNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees 
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
		}
	
	
// create employee rest api
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/employees")
	public Employee creatEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	// get employee by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee>  getEmployeeById(@PathVariable("id") int id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Employee not exist with id :"+id));
	return ResponseEntity.ok(employee);
	}
	
	//update employee rest api
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int id, @RequestBody Employee employeeDetails){
	Employee employee = employeeRepository.findById(id)
			.orElseThrow(() -> new ResourseNotFoundException("Employee not exist with id :"+id));
	employee.setName(employeeDetails.getName());
	employee.setMobile(employeeDetails.getMobile());
	employee.setEmail(employeeDetails.getEmail());
	Employee updatedEmployee = employeeRepository.save(employee);
	
	return ResponseEntity.ok(updatedEmployee);
	}
	
//	delete employee rest api
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable("id") int id){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Employee not exist with id :"+id));
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
