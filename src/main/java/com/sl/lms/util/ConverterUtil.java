package com.sl.lms.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.sl.lms.domain.Employee;
import com.sl.lms.domain.EmployeeAddress;
import com.sl.lms.domain.EmployeeLeaveBalance;
import com.sl.lms.domain.Holiday;
import com.sl.lms.domain.Office;
import com.sl.lms.dto.EmployeeAddressDTO;
import com.sl.lms.dto.EmployeeDTO;
import com.sl.lms.dto.EmployeeLeaveBalanceDTO;
import com.sl.lms.dto.HolidayDTO;
import com.sl.lms.dto.OfficeDTO;

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
		Assert.isTrue(source != null, "Employee object must not be null!");
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
	
	public static HolidayDTO convert(Holiday source, String... ignoreFields) {
		Assert.isTrue(source != null, "Holiday object must not be null!");
		HolidayDTO holidayDTO = new HolidayDTO();
		BeanUtils.copyProperties(source, holidayDTO);
		if(source.getOffice() != null) {
			OfficeDTO officeDTO = new OfficeDTO();
			BeanUtils.copyProperties(source.getOffice(), officeDTO);
			holidayDTO.setOffice(officeDTO);
		}
		return holidayDTO;
	}
	
	public static Holiday convert(HolidayDTO source, String... ignoreFields) {
		Assert.isTrue(source != null, "HolidayDTO object must not be null!");
		Holiday holiday = new Holiday();
		BeanUtils.copyProperties(source, holiday);
		if(source.getOffice() != null) {
			Office office = new Office();
			BeanUtils.copyProperties(source.getOffice(), office);
			holiday.setOffice(office);
		}
		return holiday;
	}

}
