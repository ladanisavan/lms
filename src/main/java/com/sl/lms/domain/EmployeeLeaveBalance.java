package com.sl.lms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "EMP_LEAVE_BALANCE")
@Data
@ToString(exclude = "employee")
@EqualsAndHashCode(exclude = "employee")
@NoArgsConstructor
public class EmployeeLeaveBalance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@OneToOne
	@JoinColumn(name = "EMP_ID")
	@NotNull
	private Employee employee;
	@Column(name = "CL")
	@NotNull
	private Float cl;
	@Column(name = "CO")
	@NotNull
	private Float co;
	@Column(name = "PH")
	@NotNull
	private Float ph;

	public EmployeeLeaveBalance(Float cl, Float co, Float ph) {
		this.cl = cl;
		this.co = co;
		this.ph = ph;
	}
}
