package com.sl.lms.service.impl;

import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sl.lms.domain.Holiday;
import com.sl.lms.domain.repository.HolidayRepository;
import com.sl.lms.dto.HolidayDTO;
import com.sl.lms.service.HolidayService;
import com.sl.lms.util.ConverterUtil;
import com.sl.lms.util.DTResponse;

@Service("holidayService")
public class HolidayServiceImpl implements HolidayService{

	private HolidayRepository holidayRepo;
	public HolidayServiceImpl(HolidayRepository holidayRepo) {
		this.holidayRepo = holidayRepo;
	}
	
	@Override
	public DTResponse<HolidayDTO> listHolidays(Specification<Holiday> specs, Pageable pageable) {
		return new DTResponse<HolidayDTO>(0, holidayRepo.count(), holidayRepo.count(specs),
				holidayRepo.findAll(specs, pageable).stream().map(holiday -> ConverterUtil.convert(holiday)).collect(Collectors.toList()));
	}

}
