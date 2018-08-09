package com.sl.lms.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sl.lms.domain.EmployeeLeaveBalance;

public interface EmpLeaveBalRepository extends JpaRepository<EmployeeLeaveBalance, Long>{
	Optional<EmployeeLeaveBalance> findByEmployeeEmpId(String employeeEmpId);
}
