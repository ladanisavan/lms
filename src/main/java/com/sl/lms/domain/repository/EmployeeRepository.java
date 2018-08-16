package com.sl.lms.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sl.lms.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{
	Optional<Employee> findByEmpId(String empNo);
	Optional<Employee> findByEmailId(String emailId);
	Page<Employee> findByActive(boolean active, Pageable pageable);
	long countByActive(boolean active);
	long countByEmailIdAndActive(String emailId, boolean active);
	@Modifying
	@Query("update Employee emp set emp.active = false where emp.id = ?1")
	int deactivateEmployee(Long id);
}
