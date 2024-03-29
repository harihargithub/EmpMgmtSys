package com.abj.EmpMgmtSys.service;

import java.util.List;
import java.util.Set;

import com.abj.EmpMgmtSys.model.Employee;

public interface EmployeeService {

	Employee saveEmployee(Employee employee);

	Set<Employee> fetchAll();

	// List<Employee> getAllEmployees();

	Employee findEmployeeById(long employeeId);

	Employee updateEmployeeById(long employeeId, Employee employee);

	void deleteEmployeeById(long EmployeeId);

	Set<Employee> searchEmployees(String keyword);

	List<Employee> getAllEmployeesSortedByFirstName();

	List<Employee> getAllEmployeesSortedByFirstNameDesc();

}
