package com.sl.lms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAddressDTO{

	private Long id;
	@Pattern(regexp="^([ a-zA-z0-9_#,-]{4,50})$", message="Invalid value")
	private String address1;
	@Pattern(regexp="^([ a-zA-z0-9_#,-]{0,50})$", message="Invalid value")
	private String address2;
	@NotBlank
	private String city;
	@NotBlank
	private String state;
	@NotBlank
	private String country;
	@Pattern(regexp="^([0-9]{0,6})$", message="Invalid value")
	private String zipCode;
}
