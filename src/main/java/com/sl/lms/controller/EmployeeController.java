package com.sl.lms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.lms.domain.Employee;
import com.sl.lms.domain.specification.EmployeeSpecificationsBuilder;
import com.sl.lms.dto.EmployeeDTO;
import com.sl.lms.service.EmployeeService;
import com.sl.lms.util.DTResponse;
import com.sl.lms.util.DataTablesRequest;
import com.sl.lms.util.PageRequestBuilder;

@Controller
@RequestMapping("/admin")
public class EmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	private final EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping("/listemployees")
	public String listEmployees() {
		logger.trace("EmployeeController::listEmployees called");
		//model.addAttribute("employees",employeeService.getAllRecords(true));
		return "/admin/listemployees";
	}
	
	@RequestMapping(value="/getemployees", produces=MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.POST)
	public @ResponseBody DTResponse<EmployeeDTO> getEmployees(@RequestBody final DataTablesRequest dtRequest) {
		logger.trace("EmployeeController::getEmployees called");
		return employeeService.searchEmployees(new EmployeeSpecificationsBuilder().with(dtRequest).build(),
				new PageRequestBuilder(dtRequest).withPage().withOrder().build());
	}
	
	@RequestMapping(value="/deactiveemployee", produces=MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.POST)
	public @ResponseBody ResponseEntity deactiveEmployee(@RequestParam("id") long id) {
		logger.trace("EmployeeController::deactiveEmployee called");
		if(employeeService.deactivateEmployee(id, "abc")) {
			return ResponseEntity.ok(HttpStatus.OK);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/createemployee")
	public String createEmployee(Model model) {
		logger.trace("EmployeeController::createEmployee called");
		model.addAttribute("employee", new Employee());
		return "/admin/createemployee";
	}
}