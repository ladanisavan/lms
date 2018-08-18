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

import com.sl.lms.domain.specification.EmployeeSpecificationsBuilder;
import com.sl.lms.dto.EmployeeDTO;
import com.sl.lms.service.EmployeeService;
import com.sl.lms.util.ConverterUtil;
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

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, "dob",
                new CustomDateEditor(new SimpleDateFormat(DATE_FORMAT), true));
        binder.registerCustomEditor(Date.class, "joiningDate",
                new CustomDateEditor(new SimpleDateFormat(DATE_FORMAT), true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }
	
	@GetMapping("/listemployees")
	public String listEmployees() {
		logger.trace("EmployeeController::listEmployees called");
		return "/admin/listemployees";
	}

	@RequestMapping(value = "/getemployees", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody DTResponse<EmployeeDTO> getEmployees(@RequestBody final DataTablesRequest dtRequest) {
		logger.trace("EmployeeController::getEmployees called");
		return employeeService.searchEmployees(new EmployeeSpecificationsBuilder().with(dtRequest).build(),
				new PageRequestBuilder(dtRequest).withPage().withOrder().build());
	}

	@RequestMapping(value = "/deactiveemployee", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity deactiveEmployee(@RequestParam("id") long id) {
		logger.trace("EmployeeController::deactiveEmployee called");
		if (employeeService.deactivateEmployee(id)) {
			return ResponseEntity.ok(HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/viewemployeedetails/{id}")
	public String viewEmployeeDetails(@PathVariable("id") long id, Model model) {
		logger.trace("EmployeeController::viewEmployeeDetails called");
		model.addAttribute("employee", employeeService.getEmployeeById(id).get());
		return "/admin/viewemployeedetails";
	}
	
	@GetMapping("/manageemployee/{id}")
	public String manageEmployee(@PathVariable("id") long id, Model model) {
		logger.trace("EmployeeController::manageEmployee called");
		model.addAttribute("employeeDTO", ConverterUtil.convert(employeeService.getEmployeeById(id).get(), true, true));
		return "/admin/manageemployee";
	}

	@GetMapping("/createemployee")
	public String createEmployeeView(EmployeeDTO employeeDTO) {
		logger.trace("EmployeeController::createEmployeeView called");
		return "/admin/createemployee";
	}
	
	@PostMapping("/createemployee")
	public String createEmployee(@Valid EmployeeDTO employeeDTO, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		logger.trace("EmployeeController::createEmployee called");
		if (result.hasErrors()) {
            return "/admin/createemployee";
        }
		employeeService.createEmployee(ConverterUtil.convert(employeeDTO));
		redirectAttributes.addFlashAttribute(EMP_CREATE_SUCCESS, true);
		return "redirect:/admin/listemployees";
	}
	
	private static final String EMP_CREATE_SUCCESS = "empCreateSuccess";
	private static final String DATE_FORMAT = "dd/MM/yyyy";
}