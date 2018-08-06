package com.sl.lms.domain.service;

import java.util.List;
import java.util.Optional;

import com.sl.lms.domain.Employee;

public interface EmployeeService {
	List<Employee> getAllRecords();
	Optional<Employee> getRecordByEmpId();
	Optional<Employee> getRecordByEmailId();
}
