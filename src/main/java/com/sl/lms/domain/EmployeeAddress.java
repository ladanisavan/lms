package com.sl.lms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "EMP_ADDR")
@Data
@ToString(exclude="employee")
@EqualsAndHashCode(exclude="employee")
@NoArgsConstructor
public class EmployeeAddress implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@OneToOne
	@JoinColumn(name = "EMP_ID")
	private Employee employee;
	@NotNull
	@Size(max=50)
	@Column(name = "ADDRESS1")
	private String address1;
	@Column(name = "ADDRESS2")
	@Size(max=50)
	private String address2;
	@Column(name = "CITY")
	@NotNull
	@Size(max=50)
	private String city;
	@Column(name = "STATE")
	@NotNull
	@Size(max=50)
	private String state;
	@Column(name = "COUNTRY")
	@NotNull
	@Size(max=50)
	private String country;
	@Column(name = "ZIPCODE", length=6)
	@Size(max=6, min=6)
	private String zipCode;
}
