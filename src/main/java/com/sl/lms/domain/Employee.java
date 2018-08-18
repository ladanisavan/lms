package com.sl.lms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMPLOYEE")
@Data
@NoArgsConstructor
public class Employee implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@Column(name = "FIRST_NAME")
	@NotBlank
	@Size(max=50)
	private String firstName;
	@Column(name = "MIDDLE_NAME")
	@Size(max=50)
	private String middleName;
	@Column(name = "LAST_NAME")
	@NotBlank
	@Size(max=50)
	private String lastName;
	@Column(name = "EMP_ID", unique = true)
	@NotBlank
	@Size(max=10)
	private String empId;
	@Column(name = "EMAIL_ID", unique = true)
	@NotBlank
	@Size(max=255)
	private String emailId;
	@Column(name = "DESIGNATION")
	@NotBlank
	private String designation;
	@Column(name = "DOB")
	@NotNull
	private Date dob;
	@Column(name = "GENDER")
	@NotBlank
	private String gender;
	@Column(name = "CELL_NO")
	@Size(max=10)
	private String cellNo;
	@Column(name = "WORK_PHONE")
	@Size(max=10)
	private String workPhone;
	@Column(name = "WORK_PHONE_EXT")
	@Size(max=4)
	private String workPhoneExt;
	@Column(name = "JOINING_DATE")
	@NotNull
	private Date joiningDate;
	@Column(name = "LEAVING_DATE")
	private Date leavingDate;
	@Column(name = "ACTIVE")
	private boolean active;
	@Column(name = "CREATED_DATE")
	@CreationTimestamp
	private Date createdDate;
	@Column(name = "CREATED_BY")
	@NotBlank
	@Size(max=50)
	private String createdBy;
	@Column(name = "UPDATED_DATE")
	@UpdateTimestamp
	private Date updatedDate;
	@Column(name = "UPDATED_BY")
	@Size(max=50)
	private String updatedBy;
	@OneToOne(mappedBy="employee", cascade=CascadeType.ALL)
	@NotNull
	private EmployeeAddress empAddr;
	@OneToOne(mappedBy="employee", cascade=CascadeType.ALL)
	@NotNull
	private EmployeeLeaveBalance leaveBalance;
	
	public void addEmployeeAddress(EmployeeAddress employeeAddress) {
		this.setEmpAddr(employeeAddress);
		employeeAddress.setEmployee(this);
	}
	
	public void addEmployeeLeaveBalance(EmployeeLeaveBalance employeeLeaveBalance) {
		this.setLeaveBalance(employeeLeaveBalance);
		employeeLeaveBalance.setEmployee(this);
	}
}
