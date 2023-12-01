package com.abj.EmpMgmtSys.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abj.EmpMgmtSys.model.Employee;
import com.abj.EmpMgmtSys.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping("/list")
	public String listEmployees(Model model) {
		Set<Employee> employees = this.employeeService.fetchAll();
		model.addAttribute("employees", employees);
		return "employees/list-employees";
	}

	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		this.employeeService.saveEmployee(employee);
		return "redirect:/employees/list";
	}

	@RequestMapping("/delete")
	public String deleteEmployeeById(@RequestParam("id") long employeeId) {
		this.employeeService.deleteEmployeeById(employeeId);
		return "redirect:/employees/list";
	}

	@RequestMapping("/form")
	public String showForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "/employees/employee-form";
	}

	@PostMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("id") int id, Model model) {

		// get the employee from the service
		Employee theEmployee = employeeService.findEmployeeById(id);

		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", theEmployee);

		return "employees/employee-form";
	}

	@RequestMapping("/search")
	public String searchEmployees(@RequestParam("keyword") String keyword, Model model) {
		Set<Employee> employees = employeeService.searchEmployees(keyword);
		model.addAttribute("employees", employees);
		return "employees/list-employees";
	}

	/*
	 * @RequestMapping("/list-sorted") public String listEmployeesSorted(Model
	 * model) { List<Employee> employees =
	 * employeeService.getAllEmployeesSortedByFirstName();
	 * model.addAttribute("employees", employees); return
	 * "employees/list-employees"; }
	 */
	
	@RequestMapping("/list-sorted")
	public String listEmployeesSorted(@RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder, Model model) {
	    List<Employee> employees;
	    
	    if ("desc".equals(sortOrder)) {
	        employees = employeeService.getAllEmployeesSortedByFirstNameDesc();
	    } else {
	        employees = employeeService.getAllEmployeesSortedByFirstName();
	    }
	    
	    model.addAttribute("employees", employees);
	    return "employees/list-employees";
	}


}
