package com.sl.lms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.sl.lms.domain.City;
import com.sl.lms.domain.Country;
import com.sl.lms.domain.Designation;
import com.sl.lms.domain.Office;
import com.sl.lms.domain.State;
import com.sl.lms.domain.repository.CityRepository;
import com.sl.lms.domain.repository.CountryRepository;
import com.sl.lms.domain.repository.DesignationRepository;
import com.sl.lms.domain.repository.OfficeRepository;
import com.sl.lms.domain.repository.StateRepository;
import com.sl.lms.service.MasterDataService;

@Service("masterDataService")
@Transactional
public class MasterDataServiceImpl implements MasterDataService {

	private final CountryRepository countryRepo;
	private final StateRepository stateRepo;
	private final CityRepository cityRepo;
	private final DesignationRepository desigRepo;
	private final OfficeRepository officeRepo;

	public MasterDataServiceImpl(CountryRepository countryRepo, StateRepository stateRepo, CityRepository cityRepo,
			DesignationRepository desigRepo, OfficeRepository officeRepo) {
		this.countryRepo = countryRepo;
		this.stateRepo = stateRepo;
		this.cityRepo = cityRepo;
		this.desigRepo = desigRepo;
		this.officeRepo = officeRepo;
	}

	@Override
	public List<Country> getAllCountries() {
		return countryRepo.findAll();
	}
	
	@Override
	public List<City> getAllCities() {
		return cityRepo.findAll();
	}

	@Override
	public List<State> getStatesForCountry(Long countryId) {
		Assert.isTrue(countryId != null, "Country Id must not be null!");
		return stateRepo.findByCountryId(countryId);
	}

	@Override
	public List<City> getCitiesForState(Long stateId) {
		Assert.isTrue(stateId != null, "State Id must not be null!");
		return cityRepo.findByStateId(stateId);
	}

	@Override
	public List<Designation> getAllDesignations() {
		return desigRepo.findAll();
	}

	@Override
	public List<Office> getAllOffices() {
		return officeRepo.findAll();
	}

}
