package com.sl.lms.service;

import java.util.List;
import java.util.Optional;

import com.sl.lms.domain.Employee;

public interface EmployeeService {
	Employee createEmployee(Employee employee);
	Employee updateEmployee(Employee employee);
	List<Employee> getAllRecords(boolean active);
	Optional<Employee> getEmployeeByEmpId(String empId);
	Optional<Employee> getEmployeeByEmailId(String emailId);
}
