package com.sl.lms.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO implements Serializable{

	private Long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String empId;
	private String emailId;
	private String designation;
	private Date dob;
	private String gender;
	private String cellNo;
	private Date joiningDate;
	private Date leavingDate;
	private boolean active;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
}
