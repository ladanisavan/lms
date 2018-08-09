package com.sl.lms.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.GregorianCalendar;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.sl.lms.domain.Employee;
import com.sl.lms.domain.EmployeeAddress;
import com.sl.lms.domain.EmployeeLeaveBalance;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmpLeaveBalRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private EmpLeaveBalRepository leaveBalRepository;
    
    
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
    public void whenFindByEmpId_thenReturnEployeeLeaveBal() {
    	//given
    	entityManager.persist(getNewEmpRecord());
    	entityManager.flush();
    	
    	//when
    	Optional<EmployeeLeaveBalance> found = leaveBalRepository.findByEmployeeEmpId("NAL20");
    	
    	//then
    	assertThat(found.isPresent()).isTrue();
    	assertThat(found.get().getCl()).isEqualTo(5.18f);
    }
}
