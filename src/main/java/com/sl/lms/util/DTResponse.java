package com.sl.lms.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTResponse<T> {
	private long draw;
	private long recordsTotal;
	private long recordsFiltered;
	private List<T> data;
}
