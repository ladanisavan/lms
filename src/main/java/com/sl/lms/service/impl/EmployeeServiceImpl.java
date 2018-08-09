package com.sl.lms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sl.lms.domain.Employee;
import com.sl.lms.domain.repository.EmployeeRepository;
import com.sl.lms.service.EmployeeService;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository empRepo;
	
	@Override
	public Employee createEmployee(Employee employee) {
		return empRepo.save(employee);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		return empRepo.save(employee);
	}

	@Override
	public List<Employee> getAllRecords(boolean active) {
		return empRepo.findAllByActive(active);
	}

	@Override
	public Optional<Employee> getEmployeeByEmpId(String empId) {
		Assert.notNull(empId, "empId must not be null");
		return empRepo.findByEmpId(empId);
	}

	@Override
	public Optional<Employee> getEmployeeByEmailId(String emailId) {
		Assert.notNull(emailId, "emailId must not be null");
		return empRepo.findByEmailId(emailId);
	}

}
