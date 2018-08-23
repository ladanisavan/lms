package com.sl.lms.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.sl.lms.domain.Employee;
import com.sl.lms.domain.EmployeeAddress;
import com.sl.lms.domain.EmployeeLeaveBalance;
import com.sl.lms.dto.EmployeeAddressDTO;
import com.sl.lms.dto.EmployeeDTO;
import com.sl.lms.dto.EmployeeLeaveBalanceDTO;

public class ConverterUtil {

	public static Employee convert(EmployeeDTO source, boolean includeAddr, boolean includeBal, String... ignoreFields) {
		Assert.isTrue(source != null, "EmployeeDTO object must not be null!");
		Employee employee = new Employee();
		BeanUtils.copyProperties(source, employee);
		if(source.getEmpAddr() != null && includeAddr) {
			EmployeeAddress empAddr = new EmployeeAddress();
			BeanUtils.copyProperties(source.getEmpAddr(), empAddr);
			employee.addEmployeeAddress(empAddr);
		}
		if(source.getLeaveBalance() != null && includeBal) {
			EmployeeLeaveBalance leaveBalance = new EmployeeLeaveBalance();
			BeanUtils.copyProperties(source.getLeaveBalance(), leaveBalance);
			employee.addEmployeeLeaveBalance(leaveBalance);
		}
		return employee;
	}
	
	public static EmployeeDTO convert(Employee source, boolean includeAddr, boolean includeBal, String... ignoreFields) {
		Assert.isTrue(source != null, "EmployeeDTO object must not be null!");
		EmployeeDTO employeeDTO = new EmployeeDTO();
		BeanUtils.copyProperties(source, employeeDTO);
		if(source.getEmpAddr() != null && includeAddr) {
			EmployeeAddressDTO empAddrDTO = new EmployeeAddressDTO();
			BeanUtils.copyProperties(source.getEmpAddr(), empAddrDTO);
			employeeDTO.setEmpAddr(empAddrDTO);
		}
		if(source.getLeaveBalance() != null && includeBal) {
			EmployeeLeaveBalanceDTO leaveBalanceDTO = new EmployeeLeaveBalanceDTO();
			BeanUtils.copyProperties(source.getLeaveBalance(), leaveBalanceDTO);
			employeeDTO.setLeaveBalance(leaveBalanceDTO);
		}
		return employeeDTO;
	}

}
