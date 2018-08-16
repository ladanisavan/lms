package com.sl.lms.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO implements Serializable{

	private Long id;
	@NotNull
	@NotBlank
	private String firstName;
	private String middleName;
	@NotNull
	@NotBlank
	private String lastName;
	@NotNull
	private String empId;
	@NotNull
	private String emailId;
	@NotNull
	private String designation;
	@NotNull
	private Date dob;
	@NotNull
	private String gender;
	@NotNull
	private String cellNo;
	@NotNull
	private Date joiningDate;
	private Date leavingDate;
	private boolean active;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
}
