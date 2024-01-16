package com.abj.EmpMgmtSys.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.abj.EmpMgmtSys.model.Employee;
import com.abj.EmpMgmtSys.service.EmployeeService;
import com.abj.EmpMgmtSys.util.JwtBlacklist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private JwtBlacklist jwtBlacklist;

	@GetMapping("/list")
	public ResponseEntity<?> listEmployees(@RequestHeader("Authorization") String token) {
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}

		logger.info("Token: {}", token); // Log the token

		// Check if the token is in the blacklist
		if (jwtBlacklist.contains(token)) {
			// If the token is in the blacklist, reject the request
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is blacklisted");
		}

		// If the token is not in the blacklist, continue with the request
		return ResponseEntity.ok(this.employeeService.fetchAll());
	}

	@PostMapping("/save")
	public Employee saveEmployee(@ModelAttribute("employee") Employee employee) {
		this.employeeService.saveEmployee(employee);
		return employee;
	}

	@PostMapping("/delete")
	public void deleteEmployeeById(@RequestParam("id") long employeeId) {
		this.employeeService.deleteEmployeeById(employeeId);
	}

	@PostMapping("/showFormForUpdate")
	public Employee showFormForUpdate(@RequestParam("id") int id) {
		return employeeService.findEmployeeById(id);
	}

	@GetMapping("/search")
	public Set<Employee> searchEmployees(@RequestParam("keyword") String keyword) {
		return employeeService.searchEmployees(keyword);
	}

	@GetMapping("/list-sorted")
	public List<Employee> listEmployeesSorted(
			@RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder) {
		if ("desc".equals(sortOrder)) {
			return employeeService.getAllEmployeesSortedByFirstNameDesc();
		} else {
			return employeeService.getAllEmployeesSortedByFirstName();
		}
	}

}