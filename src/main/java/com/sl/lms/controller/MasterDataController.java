package com.sl.lms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.lms.domain.City;
import com.sl.lms.domain.State;
import com.sl.lms.service.MasterDataService;

@RestController
@RequestMapping("/admin")
public class MasterDataController {
	
	Logger logger = LoggerFactory.getLogger(MasterDataController.class);
	
	private final MasterDataService mdService;
	
	public MasterDataController(MasterDataService mdService) {
		this.mdService = mdService;
	}
	
	@GetMapping("/getstates/{countryid}")
	public List<State> getStates(@PathVariable("countryid") long countryId){
		logger.trace("MasterDataController::getStates called");
		return mdService.getStatesForCountry(countryId);
	}
	
	@GetMapping("/getcities/{stateid}")
	public List<City> getCities(@PathVariable("stateid") long stateId){
		logger.trace("MasterDataController::getCities called");
		return mdService.getCitiesForState(stateId);
	}
	
	
}
