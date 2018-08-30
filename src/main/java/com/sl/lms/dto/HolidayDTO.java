package com.sl.lms.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HolidayDTO {

	private Long id;
	
	@Pattern(regexp="^([ a-zA-z]{4,30})$", message="Invalid value")
	private String name;
	@NotNull(message="Invalid value")
	private Date holidayDate;
	@Valid
	private OfficeDTO office;
	
	private boolean optional;
}