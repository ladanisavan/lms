package com.sl.lms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sl.lms.domain.Holiday;
import com.sl.lms.dto.HolidayDTO;
import com.sl.lms.util.DTResponse;

public interface HolidayService {
	DTResponse<HolidayDTO> listHolidays(Specification<Holiday> specs, Pageable pageable);
}
