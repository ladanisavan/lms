package com.sl.lms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HOLIDAYS")
@Data
@NoArgsConstructor
public class Holiday{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	
	@Column(name = "NAME")
	@NotBlank
	@Size(max=30)
	private String name;
	
	@Column(name = "HOLIDAY_DATE")
	@NotNull
	private Date holidayDate;
	
	/*@Column(name = "OFFICE_ID")
	@NotNull
	private Long officeId;*/
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="OFFICE_ID")
	@NotNull
	private Office office;
	
	@Column(name = "IS_OPTIONAL")
	private boolean optional;
}
