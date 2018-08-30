package com.sl.lms.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OfficeDTO {
	@NotNull(message="Invalid value")
	private Long id;
	private String name;
}
