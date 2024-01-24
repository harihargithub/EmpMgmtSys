package com.abj.EmpMgmtSys.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.abj.EmpMgmtSys.model.Employee;
import com.abj.EmpMgmtSys.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	private JedisPool jedisPool;

	public EmployeeController() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		this.jedisPool = new JedisPool(poolConfig, "172.22.240.1");
	}

	@GetMapping("/list")
	public ResponseEntity<?> listEmployees() {
		Set<Employee> employees = this.employeeService.fetchAll();
		logger.info("Fetched employees: {}", employees); // Log the fetched employees
		return ResponseEntity.ok(employees);
	}

	/*
	 * @GetMapping("/listxx")
	 * public ResponseEntity<?> listEmployees(@RequestHeader("Authorization") String
	 * token) {
	 * if (token != null && token.startsWith("Bearer ")) {
	 * token = token.substring(7);
	 * }
	 * 
	 * logger.info("Token: {}", token); // Log the token
	 * 
	 * // Check if the token is in the Redis blacklist
	 * try (Jedis jedis = jedisPool.getResource()) {
	 * if (jedis.exists(token)) {
	 * // If the token is in the blacklist, reject the request
	 * logger.warn("Token is blacklisted: {}", token); // Log the blacklisted token
	 * return
	 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is blacklisted");
	 * }
	 * }
	 * 
	 * // If the token is not in the blacklist, continue with the request
	 * Set<Employee> employees = this.employeeService.fetchAll();
	 * logger.info("Fetched employees: {}", employees); // Log the fetched employees
	 * return ResponseEntity.ok(employees);
	 * }
	 */

	@PostMapping("/save")
	public Employee saveEmployee(@ModelAttribute("employee") Employee employee) {
		this.employeeService.saveEmployee(employee);
		return employee;
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee updatedEmployee) {
		Employee existingEmployee = employeeService.findEmployeeById(id);
		if (existingEmployee == null) {
			// Return an error response
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		existingEmployee.setFirstName(updatedEmployee.getFirstName());
		existingEmployee.setLastName(updatedEmployee.getLastName());
		existingEmployee.setEmail(updatedEmployee.getEmail());
		employeeService.saveEmployee(existingEmployee);
		return new ResponseEntity<>(existingEmployee, HttpStatus.OK);
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