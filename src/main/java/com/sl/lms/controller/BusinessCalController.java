
package com.sl.lms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sl.lms.domain.specification.HolidaySpecsBuilder;
import com.sl.lms.dto.HolidayDTO;
import com.sl.lms.service.HolidayService;
import com.sl.lms.service.MasterDataService;
import com.sl.lms.util.ConverterUtil;
import com.sl.lms.util.DTResponse;
import com.sl.lms.util.DataTablesRequest;
import com.sl.lms.util.PageRequestBuilder;

@Controller
@RequestMapping("/admin")
public class BusinessCalController {

	Logger logger = LoggerFactory.getLogger(BusinessCalController.class);
	
	private final HolidayService holidayService;
	private final MasterDataService mdService;
	public BusinessCalController(HolidayService holidayService, MasterDataService mdService) {
		this.holidayService = holidayService;
		this.mdService = mdService;
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, "holidayDate",
                new CustomDateEditor(new SimpleDateFormat(DATE_FORMAT), true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }
	
	@GetMapping("/businesscal")
	public String viewBusinessCal(Model model) {
		model.addAttribute("offices", mdService.getAllOffices());
		return "/admin/businesscalendar";
	}
	
	@GetMapping("/addholiday")
	public String viewAddHoliday(Model model, HolidayDTO holidayDTO) {
		logger.trace("BusinessCalController::viewAddHoliday called");
		model.addAttribute("offices", mdService.getAllOffices());
		return "/admin/manageholiday";
	}
	
	@PostMapping("/addholiday")
	public String addHoliday(@Valid HolidayDTO holidayDTO, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		logger.trace("BusinessCalController::addHoliday called");
		if (result.hasErrors()) {
			model.addAttribute("offices", mdService.getAllOffices());
            return "/admin/manageholiday";
        }
		holidayService.addHoliday(ConverterUtil.convert(holidayDTO));
		redirectAttributes.addFlashAttribute(HOLIDAY_CREATE_SUCCESS, true);
		return "redirect:/admin/businesscal";
	}
	
	@GetMapping("/editholiday/{id}")
	public String viewEditHoliday(@PathVariable("id") long id, Model model) {
		logger.trace("BusinessCalController::viewEditHoliday called");
		model.addAttribute("offices", mdService.getAllOffices());
		model.addAttribute("holidayId", id);
		model.addAttribute("holidayDTO", holidayService.getHoliday(id).get());
		return "/admin/manageholiday";
	}
	
	@PostMapping("/editholiday")
	public String editHoliday(@Valid HolidayDTO holidayDTO, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		logger.trace("BusinessCalController::editHoliday called");
		if (result.hasErrors()) {
			model.addAttribute("offices", mdService.getAllOffices());
			model.addAttribute("holidayId", holidayDTO.getId());
            return "/admin/manageholiday";
        }
		holidayService.updateHoliday(ConverterUtil.convert(holidayDTO));
		redirectAttributes.addFlashAttribute(HOLIDAY_UPDATE_SUCCESS, true);
		return "redirect:/admin/businesscal";
	}
	
	@RequestMapping(value = "/deleteholiday", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity deleteHoliday(@RequestParam("id") long id) {
		logger.trace("EmployeeController::deleteHoliday called");
		if (holidayService.deleteHoliday(id)) {
			return ResponseEntity.ok(HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(value = "/listholidays", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody DTResponse<HolidayDTO> listHolidays(@RequestBody final DataTablesRequest dtRequest, 
			@RequestParam("officeId") Long officeId, @RequestParam("year") String year,
			@RequestParam("optional") boolean optional) {
		logger.trace("BusinessCalController::listHolidays called");
		return holidayService.listHolidays(new HolidaySpecsBuilder().with(officeId, year, optional).build(),
				new PageRequestBuilder(dtRequest).withPage().withOrder().build());
	}
	
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String HOLIDAY_CREATE_SUCCESS = "holidayCreateSuccess";
	private static final String HOLIDAY_UPDATE_SUCCESS = "holidayUpdateSuccess";
}
