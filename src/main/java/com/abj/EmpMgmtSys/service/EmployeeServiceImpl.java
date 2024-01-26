package com.abj.EmpMgmtSys.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abj.EmpMgmtSys.model.Employee;
import com.abj.EmpMgmtSys.repository.EmployeeJpaRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

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
	public Employee findEmployeeById(long id) {
		return this.employeeJpaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id"));
	}

	@Override
	public Employee updateEmployeeById(long employeeId, Employee employee) {
		Employee existingEmployee = this.findEmployeeById(employeeId);
		if (existingEmployee == null) {
			throw new IllegalArgumentException("Invalid Employee Id");
		}
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		return this.employeeJpaRepository.save(existingEmployee);
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
