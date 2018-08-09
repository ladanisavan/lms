package com.sl.lms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.lms.service.EmployeeService;

@Controller
@RequestMapping("/admin")
public class EmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	private final EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping("/listemployees")
	public String getAllEmployees(Model model) {
		logger.trace("EmployeeController::getAllEmployees called");
		model.addAttribute("employees",employeeService.getAllRecords(true));
		return "/admin/listemployees";
	}
}