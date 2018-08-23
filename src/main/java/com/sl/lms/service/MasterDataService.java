package com.sl.lms.service;

import java.util.List;

import com.sl.lms.domain.City;
import com.sl.lms.domain.Country;
import com.sl.lms.domain.Designation;
import com.sl.lms.domain.State;

public interface MasterDataService {
	List<Country> getAllCountries();
	List<City> getAllCities();
	List<State> getStatesForCountry(Long countryId);
	List<City> getCitiesForState(Long stateId);
	List<Designation> getAllDesignations();
}
