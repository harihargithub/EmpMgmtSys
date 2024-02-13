package com.abj.EmpMgmtSys.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;

import com.abj.EmpMgmtSys.model.Employee;
import com.abj.EmpMgmtSys.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/list")
	public ResponseEntity<?> listEmployees() {
		Set<Employee> employees = this.employeeService.fetchAll();
		logger.info("Fetched employees: {}", employees); // Log the fetched employees
		return ResponseEntity.ok(employees);
	}

	@PostMapping("/save")
	public Employee saveEmployee(@RequestBody Employee employee) {
		return this.employeeService.saveEmployee(employee);
	}

	@PutMapping("/update")
	public Employee updateEmployee(@RequestBody Employee updatedEmployee) {
		logger.info("Received update request for employee with ID: {}", updatedEmployee.getId());
		return employeeService.updateEmployeeById(updatedEmployee.getId(), updatedEmployee);
	}

	@PostMapping("/delete")
	public void deleteEmployeeById(@RequestParam("id") long employeeId) {
		this.employeeService.deleteEmployeeById(employeeId);
	}

	@GetMapping("/search")
	public Set<Employee> searchEmployees(@RequestParam("keyword") String keyword) {
		return employeeService.searchEmployees(keyword);
	}

	@GetMapping("/list-sorted/{sortOrder}")
	public List<Employee> listEmployeesSorted(@PathVariable String sortOrder) {
		if ("desc".equals(sortOrder)) {
			return employeeService.getAllEmployeesSortedByFirstNameDesc();
		} else {
			return employeeService.getAllEmployeesSortedByFirstName();
		}
	}
}