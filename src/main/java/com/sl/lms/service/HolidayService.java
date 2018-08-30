package com.sl.lms.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sl.lms.domain.Holiday;
import com.sl.lms.dto.HolidayDTO;
import com.sl.lms.util.DTResponse;

public interface HolidayService {
	void addHoliday(Holiday holiday);

	void updateHoliday(Holiday holiday);

	boolean deleteHoliday(Long id);

	Optional<Holiday> getHoliday(Long id);

	DTResponse<HolidayDTO> listHolidays(Specification<Holiday> specs, Pageable pageable);
}
