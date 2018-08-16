package com.sl.lms.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sl.lms.domain.Employee;
import com.sl.lms.dto.EmployeeDTO;
import com.sl.lms.util.DTResponse;

public interface EmployeeService {
	Employee createEmployee(Employee employee);
	Employee updateEmployee(Employee employee);
	boolean deactivateEmployee(Long id);
	DTResponse<EmployeeDTO> searchEmployees(Specification<Employee> specs, Pageable pageable);
	Optional<Employee> getEmployeeByEmpId(String empId);
	Optional<Employee> getEmployeeByEmailId(String emailId);
	Optional<Employee> getEmployeeById(Long id);
	boolean isEmployeeExists(String emailId, boolean actie);
}
