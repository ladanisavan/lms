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

import com.sl.lms.domain.specification.EmployeeSpecsBuilder;
import com.sl.lms.dto.EmployeeDTO;
import com.sl.lms.service.EmployeeService;
import com.sl.lms.service.MasterDataService;
import com.sl.lms.util.ConverterUtil;
import com.sl.lms.util.DTResponse;
import com.sl.lms.util.DataTablesRequest;
import com.sl.lms.util.PageRequestBuilder;

@Controller
@RequestMapping("/admin")
public class EmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	private final EmployeeService employeeService;
	private final MasterDataService mdService;

	public EmployeeController(EmployeeService employeeService, MasterDataService mdService) {
		this.employeeService = employeeService;
		this.mdService = mdService;
	}

	@InitBinder
    public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, "createdDate",
                new CustomDateEditor(new SimpleDateFormat(DATE_FORMAT), true));
        binder.registerCustomEditor(Date.class, "dob",
                new CustomDateEditor(new SimpleDateFormat(DATE_FORMAT), true));
        binder.registerCustomEditor(Date.class, "joiningDate",
                new CustomDateEditor(new SimpleDateFormat(DATE_FORMAT), true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }
	
	@GetMapping("/listemployees")
	public String employeeListView() {
		logger.trace("EmployeeController::employeeListView called");
		return "/admin/listemployees";
	}
	
	@GetMapping("/listinactiveemprecords")
	public String inactiveEmpListView() {
		logger.trace("EmployeeController::inactiveEmpListView called");
		return "/admin/listinactiveemprecords";
	}

	@RequestMapping(value = "/getemployees", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody DTResponse<EmployeeDTO> getEmployees(@RequestBody final DataTablesRequest dtRequest) {
		logger.trace("EmployeeController::getEmployees called");
		return employeeService.searchEmployees(new EmployeeSpecsBuilder().with(dtRequest).build(),
				new PageRequestBuilder(dtRequest).withPage().withOrder().build(), true);
	}
	
	@RequestMapping(value = "/getinactiveemprecords", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody DTResponse<EmployeeDTO> getInactiveEmployeeRecords(@RequestBody final DataTablesRequest dtRequest) {
		logger.trace("EmployeeController::getInactiveEmployeeRecords called");
		return employeeService.searchEmployees(new EmployeeSpecsBuilder().with(dtRequest).build(),
				new PageRequestBuilder(dtRequest).withPage().withOrder().build(), false);
	}

	@RequestMapping(value = "/changeemprecordstatus", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity changeEmpRecordStatus(@RequestParam("id") long id, @RequestParam("status") boolean status) {
		logger.trace("EmployeeController::changeEmpRecordStatus called");
		if (employeeService.changeRecordStatus(id, status)) {
			return ResponseEntity.ok(HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/viewemployeedetails/{id}")
	public String employeeDetailView(@PathVariable("id") long id, Model model) {
		logger.trace("EmployeeController::employeeDetailView called");
		model.addAttribute("employee", employeeService.getEmployeeById(id).get());
		return "/admin/viewemployeedetails";
	}
	
	@GetMapping("/manageemployee/{id}")
	public String manageEmployeeView(@PathVariable("id") long id, Model model) {
		logger.trace("EmployeeController::manageEmployeeView called");
		model.addAttribute("employeeDTO", ConverterUtil.convert(employeeService.getEmployeeById(id).get(), true, true));
		model.addAttribute("countries", mdService.getAllCountries());
		model.addAttribute("designations", mdService.getAllDesignations());
		return "/admin/manageemployee";
	}
	
	@PostMapping("/manageemployee")
	public String manageEmployee(@Valid EmployeeDTO employeeDTO, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		logger.trace("EmployeeController::manageEmployee called");
		if (result.hasErrors()) {
			model.addAttribute("countries", mdService.getAllCountries());
			model.addAttribute("designations", mdService.getAllDesignations());
            return "/admin/manageemployee";
        }
		employeeService.updateEmployee(ConverterUtil.convert(employeeDTO, true, true));
		redirectAttributes.addFlashAttribute(EMP_UPDATE_SUCCESS, true);
		return "redirect:/admin/listemployees";
	}
	
	@GetMapping("/createemployee")
	public String createEmployeeView(EmployeeDTO employeeDTO, Model model) {
		logger.trace("EmployeeController::createEmployeeView called");
		model.addAttribute("countries", mdService.getAllCountries());
		model.addAttribute("designations", mdService.getAllDesignations());
		return "/admin/createemployee";
	}
	
	@PostMapping("/createemployee")
	public String createEmployee(@Valid EmployeeDTO employeeDTO, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		logger.trace("EmployeeController::createEmployee called");
		if (result.hasErrors()) {
			model.addAttribute("countries", mdService.getAllCountries());
			model.addAttribute("designations", mdService.getAllDesignations());
            return "/admin/createemployee";
        }
		employeeService.createEmployee(ConverterUtil.convert(employeeDTO, true, false));
		redirectAttributes.addFlashAttribute(EMP_CREATE_SUCCESS, true);
		return "redirect:/admin/listemployees";
	}
	
	private static final String EMP_CREATE_SUCCESS = "empCreateSuccess";
	private static final String EMP_UPDATE_SUCCESS = "empUpdateSuccess";
	private static final String DATE_FORMAT = "dd/MM/yyyy";
}