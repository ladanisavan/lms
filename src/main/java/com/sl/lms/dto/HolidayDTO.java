package com.sl.lms.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HolidayDTO {

	private Long id;
	
	private String name;
	
	private Date holidayDate;
	
	private OfficeDTO office;
	
	private boolean optional;
}