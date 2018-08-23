package com.sl.lms.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sl.lms.configuration.auth.helper.CurrentUserHolder;
import com.sl.lms.domain.Employee;
import com.sl.lms.domain.EmployeeLeaveBalance;
import com.sl.lms.domain.repository.EmployeeRepository;
import com.sl.lms.domain.specification.EmployeeSpecification;
import com.sl.lms.domain.specification.SearchCriteria;
import com.sl.lms.dto.EmployeeDTO;
import com.sl.lms.service.EmployeeService;
import com.sl.lms.util.ConverterUtil;
import com.sl.lms.util.DTResponse;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository empRepo;
	private CurrentUserHolder currentUserHolder;

	public EmployeeServiceImpl(EmployeeRepository empRepo, CurrentUserHolder currentUserHolder) {
		this.empRepo = empRepo;
		this.currentUserHolder = currentUserHolder;
	}

	@Override
	public Employee createEmployee(Employee employee) {
		employee.addEmployeeLeaveBalance(new EmployeeLeaveBalance(0.00f, 0.00f, 0.00f));
		employee.setCreatedBy(currentUserHolder.currentUserEmail());
		employee.setActive(true);
		return empRepo.save(employee);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		employee.setUpdatedBy(currentUserHolder.currentUserEmail());
		return empRepo.save(employee);
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

	@Override
	public DTResponse<EmployeeDTO> searchEmployees(Specification<Employee> specs, Pageable pageable, boolean active) {
		
		Specification<Employee> defaultActiveSpec = new EmployeeSpecification(new SearchCriteria("active", ":", active));
		if (specs != null) {
			specs = specs.and(defaultActiveSpec);
		} else {
			specs = defaultActiveSpec;
		}
		return new DTResponse<EmployeeDTO>(0, empRepo.countByActive(active), empRepo.count(specs),
				empRepo.findAll(specs, pageable).stream().map(emp -> ConverterUtil.convert(emp, false, false)).collect(Collectors.toList()));
	}

	@Override
	public Optional<Employee> getEmployeeById(Long id) {
		return empRepo.findById(id);
	}
	
	@Override
	public boolean isEmployeeExists(String emailId, boolean active) {
		return empRepo.countByEmailIdAndActive(emailId, active)>0;
	}

	@Override
	public boolean changeRecordStatus(Long id, boolean active) {
		Optional<Employee> emp = empRepo.findById(id);
		Assert.isTrue(emp.isPresent(), "Employee record not found by id: " + id);
		emp.get().setActive(active);
		emp.get().setUpdatedBy(currentUserHolder.currentUserEmail());
		empRepo.save(emp.get());
		return true;
	}

}
