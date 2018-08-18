package com.sl.lms.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO implements Serializable{

	private Long id;
	@Pattern(regexp="^([a-zA-z]{4,32})$", message="Invalid value")
	private String firstName;
	@Pattern(regexp="^([a-zA-z]{0,32})$", message="Invalid value")
	private String middleName;
	@Pattern(regexp="^([a-zA-z]{4,32})$", message="Invalid value")
	private String lastName;
	@Pattern(regexp="^([a-zA-z0-9]{4,6})$", message="Invalid value")
	private String empId;
	@NotBlank(message="Invalid value")
	@Email(message="Invalid value")
	private String emailId;
	@NotBlank(message="Invalid value")
	private String designation;
	@NotNull(message="Invalid value")
	private Date dob;
	@NotBlank(message="Invalid value")
	private String gender;
	@Pattern(regexp="^([0-9]{10})$", message="Invalid value")
	private String cellNo;
	@Pattern(regexp="^([0-9]{0,10})$", message="Invalid value")
	private String workPhone;
	@Pattern(regexp="^([0-9]{0,4})$", message="Invalid value")
	private String workPhoneExt;
	@NotNull(message="Invalid value")
	private Date joiningDate;
	private Date leavingDate;
	private boolean active;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	
	@Valid
	private EmployeeAddressDTO empAddr;
}