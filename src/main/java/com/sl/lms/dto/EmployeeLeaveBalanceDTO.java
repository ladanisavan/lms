package com.sl.lms.dto;

import javax.validation.constraints.Digits;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeLeaveBalanceDTO{

	private Long id;
	@Digits(integer = 2, fraction = 2, message="Invalid value")
	private float cl;
	@Digits(integer = 2, fraction = 2, message="Invalid value")
	private float co;
	@Digits(integer = 2, fraction = 2, message="Invalid value")
	private float ph;
}
