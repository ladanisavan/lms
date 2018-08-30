package com.sl.lms.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.sl.lms.domain.Holiday;
import com.sl.lms.domain.Office;
import com.sl.lms.domain.repository.HolidayRepository;
import com.sl.lms.domain.repository.OfficeRepository;
import com.sl.lms.dto.HolidayDTO;
import com.sl.lms.service.HolidayService;
import com.sl.lms.util.ConverterUtil;
import com.sl.lms.util.DTResponse;

@Service("holidayService")
public class HolidayServiceImpl implements HolidayService{

	private HolidayRepository holidayRepo;
	private OfficeRepository officeRepo;
	public HolidayServiceImpl(HolidayRepository holidayRepo, OfficeRepository officeRepo) {
		this.holidayRepo = holidayRepo;
		this.officeRepo = officeRepo;
	}
	
	@Override
	public DTResponse<HolidayDTO> listHolidays(Specification<Holiday> specs, Pageable pageable) {
		return new DTResponse<HolidayDTO>(0, holidayRepo.count(), holidayRepo.count(specs),
				holidayRepo.findAll(specs, pageable).stream().map(holiday -> ConverterUtil.convert(holiday)).collect(Collectors.toList()));
	}

	@Override
	public void addHoliday(Holiday holiday) {
		Assert.notNull(holiday, "holiday must not be null");
		//for all offices
		if(holiday.getOffice().getId()==-1) {
			for(Office office : officeRepo.findAll()) {
				Holiday newOne = new Holiday();
				newOne.setName(holiday.getName());
				newOne.setHolidayDate(holiday.getHolidayDate());
				newOne.setOptional(holiday.isOptional());
				newOne.setOffice(office);
				holidayRepo.save(newOne);
			}
		}else {
			holiday.setOffice(officeRepo.findById(holiday.getOffice().getId()).get());
			holidayRepo.save(holiday);
		}
	}

	@Override
	public Optional<Holiday> getHoliday(Long id) {
		return holidayRepo.findById(id);
	}

	@Override
	public void updateHoliday(Holiday holiday) {
		holiday.setOffice(officeRepo.findById(holiday.getOffice().getId()).get());
		holidayRepo.save(holiday);
	}

	@Override
	public boolean deleteHoliday(Long id) {
		holidayRepo.deleteById(id);
		return true;
	}

}
