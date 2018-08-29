
package com.sl.lms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.lms.domain.specification.HolidaySpecsBuilder;
import com.sl.lms.dto.HolidayDTO;
import com.sl.lms.service.HolidayService;
import com.sl.lms.service.MasterDataService;
import com.sl.lms.util.DTResponse;
import com.sl.lms.util.DataTablesRequest;
import com.sl.lms.util.PageRequestBuilder;

@Controller
@RequestMapping("/admin")
public class SettingsController {

	Logger logger = LoggerFactory.getLogger(SettingsController.class);
	
	private final HolidayService holidayService;
	private final MasterDataService mdService;
	public SettingsController(HolidayService holidayService, MasterDataService mdService) {
		this.holidayService = holidayService;
		this.mdService = mdService;
	}
	
	@GetMapping("/settings")
	public String viewSettingsHome() {
		return "/admin/settings";
	}
	
	@GetMapping("/businesscal")
	public String viewBusinessCal(Model model) {
		model.addAttribute("offices", mdService.getAllOffices());
		return "/admin/businesscalendar";
	}
	
	@RequestMapping(value = "/listholidays", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody DTResponse<HolidayDTO> listHolidays(@RequestBody final DataTablesRequest dtRequest, 
			@RequestParam("officeId") Long officeId, @RequestParam("year") String year,
			@RequestParam("optional") boolean optional) {
		logger.trace("EmployeeController::getEmployees called");
		return holidayService.listHolidays(new HolidaySpecsBuilder().with(officeId, year, optional).build(),
				new PageRequestBuilder(dtRequest).withPage().withOrder().build());
	}
}
