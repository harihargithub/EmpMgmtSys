package com.abj.EmpMgmtSys.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abj.EmpMgmtSys.model.Employee;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	Set<Employee> searchEmployees(@Param("keyword") String keyword);

	List<Employee> findByOrderByFirstNameAsc();
	
	List<Employee> findAllByOrderByFirstNameDesc();

}
