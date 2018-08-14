package com.sl.lms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sl.lms.configuration.auth.helper.CurrentUserHolder;
import com.sl.lms.domain.Employee;
import com.sl.lms.domain.repository.EmployeeRepository;
import com.sl.lms.domain.specification.EmployeeSpecification;
import com.sl.lms.domain.specification.SearchCriteria;
import com.sl.lms.dto.EmployeeDTO;
import com.sl.lms.service.EmployeeService;
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
		return empRepo.save(employee);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
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
	public DTResponse<EmployeeDTO> searchEmployees(Specification<Employee> specs, Pageable pageable) {
		//returning only active employee records
		if (specs != null) {
			specs = specs.and(new EmployeeSpecification(new SearchCriteria("active",":",true)));
		} else {
			specs = new EmployeeSpecification(new SearchCriteria("active",":",true));
		}
		List<EmployeeDTO> resultList = empRepo.findAll(specs, pageable).stream().map(this::mapToEmployeeDto)
				.collect(Collectors.toList());

		return new DTResponse<EmployeeDTO>(0, empRepo.countByActive(true), empRepo.count(specs), resultList);
	}

	private EmployeeDTO mapToEmployeeDto(Employee emp) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		BeanUtils.copyProperties(emp, employeeDTO);
		return employeeDTO;
	}

	@Override
	public boolean deactivateEmployee(Long id, String updatedBy) {
		Optional<Employee> emp = empRepo.findById(id);
		Assert.isTrue(emp.isPresent(),"Employee record not found by id: "+id);
		emp.get().setActive(false);
		emp.get().setUpdatedBy(currentUserHolder.currentUserEmail());
		empRepo.save(emp.get());
		return true;
	}

}
