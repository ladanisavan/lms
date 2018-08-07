package com.sl.lms.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMPLOYEE")
@Data
@NoArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@Column(name = "FIRST_NAME", nullable=false, length=50)
	private String firstName;
	@Column(name = "MIDDLE_NAME", nullable=true, length=50)
	private String middleName;
	@Column(name = "LAST_NAME", nullable=false, length=50)
	private String lastName;
	@Column(name = "EMP_ID", nullable = false, unique = true, length=10)
	private String empId;
	@Column(name = "EMAIL_ID", nullable = false, unique = true, length=255)
	private String emailId;
	@Column(name = "DESIGNATION", nullable = false)
	private String designation;
	@Column(name = "DOB", nullable = false)
	private Date dob;
	@Column(name = "GENDER", nullable = false)
	private String gender;
	@Column(name = "CELL_NO", nullable = true, length=10)
	private String cellNo;
	@Column(name = "JOINING_DATE", nullable = false)
	private Date joiningDate;
	@Column(name = "LEAVING_DATE", nullable = true)
	private Date leavingDate;
	@Column(name = "ACTIVE", nullable = false)
	private boolean active;
	@Column(name = "CREATED_DATE", nullable = false)
	@CreationTimestamp
	private Date createdDate;
	@Column(name = "CREATED_BY", nullable = false, length=50)
	private String createdBy;
	@Column(name = "UPDATE_DATE", nullable = true)
	@UpdateTimestamp
	private Date updatedDate;
	@Column(name = "UPDATED_BY", nullable = true, length=50)
	private String updatedBy;
	@OneToOne(mappedBy="employee", cascade=CascadeType.ALL)
	private EmployeeAddress empAddr;
	@OneToOne(mappedBy="employee", cascade=CascadeType.ALL)
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
