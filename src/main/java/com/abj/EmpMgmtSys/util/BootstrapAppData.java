package com.abj.EmpMgmtSys.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.abj.EmpMgmtSys.model.Employee;
import com.abj.EmpMgmtSys.model.Role;
import com.abj.EmpMgmtSys.model.User;
import com.abj.EmpMgmtSys.repository.EmployeeJpaRepository;
import com.abj.EmpMgmtSys.repository.UserJpaRepository;

@Configuration
public class BootstrapAppData {

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private EmployeeJpaRepository employeeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void loadUsers(ApplicationReadyEvent event) {

		if (userJpaRepository.count() > 0) {
			return;
		}

		// addding users and roles

		User kiran = new User("kiran", this.passwordEncoder.encode("welcome"));
		User vinay = new User("vinay", this.passwordEncoder.encode("welcome"));
		User ramesh = new User("ramesh", this.passwordEncoder.encode("welcome"));

		Role userRole = new Role("ROLE_USER");
		Role adminRole = new Role("ROLE_ADMIN");
		Role superAdminRole = new Role("ROLE_SUPER_ADMIN");

		kiran.addRole(userRole);

		vinay.addRole(userRole);
		vinay.addRole(adminRole);

		ramesh.addRole(userRole);
		ramesh.addRole(adminRole);
		ramesh.addRole(superAdminRole);

		this.userJpaRepository.save(kiran);
		this.userJpaRepository.save(vinay);
		this.userJpaRepository.save(ramesh);
	}

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void loadEmployees(ApplicationReadyEvent event) {

		if (employeeRepository.count() >= 10) {
			return;
		}

		Employee emp1 = new Employee();
		emp1.setFirstName("Johny");
		emp1.setLastName("Meir");
		emp1.setEmail("it@gg.com");

		Employee emp2 = new Employee();
		emp2.setFirstName("Janey");
		emp2.setLastName("Mithow");
		emp2.setEmail("hr@ah.in");

		Employee emp3 = new Employee();
		emp3.setFirstName("Michsael");
		emp3.setLastName("Socher");
		emp3.setEmail("sales@abj.edu");

		Employee emp4 = new Employee();
		emp4.setFirstName("Wilson");
		emp4.setLastName("Finao");
		emp4.setEmail("fin@co.in");

		this.employeeRepository.save(emp1);
		this.employeeRepository.save(emp2);
		this.employeeRepository.save(emp3);
		this.employeeRepository.save(emp4);
	}
}
