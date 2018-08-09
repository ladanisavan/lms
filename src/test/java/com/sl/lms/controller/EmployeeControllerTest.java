package com.sl.lms.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.sl.lms.configuration.SecurityConfigurationTest;
import com.sl.lms.domain.Employee;
import com.sl.lms.domain.EmployeeAddress;
import com.sl.lms.domain.EmployeeLeaveBalance;
import com.sl.lms.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@ContextConfiguration(classes={SecurityConfigurationTest.class})
public class EmployeeControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private EmployeeService service;
	
	private Employee getNewEmpRecord() {
    	Employee emp = new Employee();
    	emp.setFirstName("John");
    	emp.setLastName("Doe");
    	emp.setEmpId("NAL20");
    	emp.setDesignation("Sr. Software Engineer");
    	emp.setEmailId("john.doe@someemail.com");
    	emp.setDob(new GregorianCalendar(1987, 11, 25).getTime());
    	emp.setGender("Male");
    	emp.setJoiningDate(new GregorianCalendar(2014, 04, 25).getTime());
    	emp.setCellNo("1234567890");
    	emp.setActive(true);
    	emp.setCreatedBy("Mike Hudde");
    	
    	EmployeeAddress addr = new EmployeeAddress();
    	addr.setAddress1("Main street");
    	addr.setCity("New York");
    	addr.setState("New York");
    	addr.setCountry("USA");
    	addr.setZipCode("100001");
    	
    	emp.addEmployeeAddress(addr);
    	
    	EmployeeLeaveBalance leaveBal = new EmployeeLeaveBalance();
    	leaveBal.setCl(5.18f);
    	leaveBal.setCo(2.3f);
    	leaveBal.setPh(1f);
    	
    	emp.addEmployeeLeaveBalance(leaveBal);
    	return emp;
    }
	
	@Test
	@WithMockUser(username = "username", roles={"ADMIN"})
	public void givenAllActiveEmployees_whenListEmployees_thenReturnAllActiveEmployees()
	  throws Exception {
	     
	    Employee john = getNewEmpRecord();
	 
	    when(service.getAllRecords(true)).thenReturn(Arrays.asList(john));
	 
	    mvc.perform(get("/admin/listemployees"))
	      .andExpect(status().isOk())
	      .andExpect(view().name("/admin/listemployees"))
	      .andExpect(model().attribute("employees", hasSize(1)))
	      .andExpect(model().attribute("employees", hasItem(allOf(
	    		  hasProperty("firstName", is("John")),
	    		  hasProperty("lastName", is("Doe")),
	    		  hasProperty("emailId", is("john.doe@someemail.com"))
          )))).andDo(MockMvcResultHandlers.print());
	}
}
