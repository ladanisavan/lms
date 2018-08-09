package com.sl.lms.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sl.lms.domain.Employee;
import com.sl.lms.domain.repository.EmployeeRepository;
import com.sl.lms.service.impl.EmployeeServiceImpl;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl();
        }
    }
	
	@Autowired
    private EmployeeService employeeService;
 
    @MockBean
    private EmployeeRepository employeeRepository;
    
    
    private Employee getEmployee() {
    	 Employee john = new Employee();
         john.setFirstName("John");
         john.setLastName("Doe");
         john.setEmailId("john.doe@someemail.com");
         john.setEmpId("NAL20");
         return john;
    }
    
    @Before
    public void setUp() {
        Employee john = getEmployee();
        
        Mockito.when(employeeRepository.findByEmailId(john.getEmailId()))
          .thenReturn(Optional.of(john));
        
        Mockito.when(employeeRepository.findByEmpId(john.getEmpId()))
        .thenReturn(Optional.of(john));
        
        Mockito.when(employeeRepository.save(john))
        .thenReturn(john);
        
        Mockito.when(employeeRepository.findAllByActive(true))
        .thenReturn(new ArrayList<Employee>() {{add(john);}});
    }
    
    @Test
    public void whenCreateEmployeeIsCalled_thenReturnEmployee() {
    	//given
        Employee john = getEmployee();
        
        //when
        Employee found = employeeService.createEmployee(john);
      
        //then
        assertThat(found).isEqualTo(john);
     }
    
    @Test
    public void whenUpdateEmployeeIsCalled_thenReturnEmployee() {
    	//given
        Employee john = getEmployee();
        
        //when
        Employee found = employeeService.updateEmployee(john);
      
        //then
        assertThat(found).isEqualTo(john);
     }
    
    @Test
    public void whenGetAllRecordsIsCalled_thenReturnListOfEmployees() {
    	//given
        
        //when
        List<Employee> found = employeeService.getAllRecords(true);
      
        //then
        assertThat(found).isNotNull();
        assertThat(found.size()).isGreaterThan(0);
     }
    
    @Test
    public void whenValidEmailId_thenEmployeeShouldBeFound() {
    	//given
        String emailId = "john.doe@someemail.com";
        
        //when
        Optional<Employee> found = employeeService.getEmployeeByEmailId(emailId);
      
        //then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getFirstName()).isEqualTo("John");
     }
    
    @Test
    public void whenValidEmpId_thenEmployeeShouldBeFound() {
    	//given
        String empId = "NAL20";
        
        //when
        Optional<Employee> found = employeeService.getEmployeeByEmpId(empId);
      
        //then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getFirstName()).isEqualTo("John");
     }
    
    
}
