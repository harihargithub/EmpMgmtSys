package com.abj.EmpMgmtSys.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abj.EmpMgmtSys.model.Employee;
import com.abj.EmpMgmtSys.repository.EmployeeJpaRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeJpaRepository employeeJpaRepository;

	@Override
	public Employee saveEmployee(Employee employee) {
		if (employee != null) {
			return this.employeeJpaRepository.save(employee);
		} else {
			// Handle the case where the employee is null
			// You could throw an exception or return null, depending on your use case
			throw new IllegalArgumentException("Employee cannot be null");
		}
	}

	@Override
	public Set<Employee> fetchAll() {
		return Set.copyOf(this.employeeJpaRepository.findAll());
	}

	@Override
	public Employee findEmployeeById(long employeeId) {
		return this.employeeJpaRepository.findById(employeeId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id"));
	}

	@Override
	public Employee updateEmployeeById(long employeeId, Employee updatedEmployee) {
		logger.info("Updating employee with ID: {}", employeeId);
		Employee existingEmployee = this.findEmployeeById(employeeId);
		existingEmployee.setFirstName(updatedEmployee.getFirstName());
		existingEmployee.setLastName(updatedEmployee.getLastName());
		existingEmployee.setEmail(updatedEmployee.getEmail());
		Employee result = this.employeeJpaRepository.save(existingEmployee);
		logger.info("Update result: {}", result);
		return result;
	}

	@Override
	public void deleteEmployeeById(long EmployeeId) {
		this.employeeJpaRepository.deleteById(EmployeeId);
	}

	@Override
	public Set<Employee> searchEmployees(String keyword) {
		return employeeJpaRepository.searchEmployees(keyword);
	}

	@Override
	public List<Employee> getAllEmployeesSortedByFirstName() {
		return employeeJpaRepository.findByOrderByFirstNameAsc();
	}

	@Override
	public List<Employee> getAllEmployeesSortedByFirstNameDesc() {
		return employeeJpaRepository.findAllByOrderByFirstNameDesc();
	}

}
