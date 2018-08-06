package com.sl.lms.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sl.lms.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	Optional<Employee> findByEmpId(String empNo);
	Optional<Employee> findByEmailId(String emailId);
}
