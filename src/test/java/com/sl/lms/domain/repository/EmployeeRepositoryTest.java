package com.sl.lms.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.GregorianCalendar;
import java.util.List;
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
public class EmployeeRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private EmployeeRepository employeeRepo;
    
    private Employee getNewEmpRecord() {
    	Employee emp = new Employee();
    	emp.setFirstName("John");
    	emp.setLastName("Doe");
    	emp.setEmpId("NAL20");
    	emp.setDesignation("Sr. Software Engineer");
    	emp.setEmailId("john.doe@nividous.com");
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
    public void whenFindByEmpId_thenReturnEmployee() {
    	//given
    	entityManager.persist(getNewEmpRecord());
    	entityManager.flush();
    	
    	//when
    	Optional<Employee> found = employeeRepo.findByEmpId("NAL20");
    	
    	//then
    	assertThat(found.isPresent()).isTrue();
    	assertThat(found.get().getFirstName()).isEqualTo("John");
    }
    
    @Test
    public void whenFindByEmailId_thenReturnEmployee() {
    	//given
    	entityManager.persist(getNewEmpRecord());
    	entityManager.flush();
    	
    	//when
    	Optional<Employee> found = employeeRepo.findByEmailId("john.doe@nividous.com");
    	
    	//then
    	assertThat(found.isPresent()).isTrue();
    	assertThat(found.get().getFirstName()).isEqualTo("John");
    }
    
    @Test
    public void whenFindByEmailId_thenReturnEmployeeAddress() {
    	//given
    	entityManager.persist(getNewEmpRecord());
    	entityManager.flush();
    	
    	//when
    	Optional<Employee> found = employeeRepo.findByEmailId("john.doe@nividous.com");
    	
    	//then
    	assertThat(found.isPresent()).isTrue();
    	assertThat(found.get().getEmpAddr().getCity()).isEqualTo("New York");
    }
    
    @Test
    public void whenFindByEmailId_thenReturnEmployeeLeaveBalace() {
    	//given
    	entityManager.persist(getNewEmpRecord());
    	entityManager.flush();
    	
    	//when
    	Optional<Employee> found = employeeRepo.findByEmailId("john.doe@nividous.com");
    	
    	//then
    	assertThat(found.isPresent()).isTrue();
    	assertThat(found.get().getLeaveBalance().getPh()).isEqualTo(1f);
    }
    
    @Test
    public void whenFindAllByActive_thenReturnAllActiveEmployees() {
    	//given
    	entityManager.persist(getNewEmpRecord());
    	entityManager.flush();
    	
    	//when
    	List<Employee> found = employeeRepo.findAllByActive(true);
    	
    	//then
    	assertThat(found.isEmpty()).isFalse();
    	assertThat(found.size()).isEqualTo(1);
    }
    
    @Test
    public void whenUpdateLeaveBal_thenReturnUpdatedValues() {
    	//given
    	entityManager.persist(getNewEmpRecord());
    	entityManager.flush();
    	
    	Optional<Employee> orgRecord = employeeRepo.findByEmailId("john.doe@nividous.com");
    	
    	Employee emp = orgRecord.get();
    	EmployeeLeaveBalance newBalance = new EmployeeLeaveBalance();
    	newBalance.setCl(4.02f);
    	newBalance.setCo(3.5f);
    	newBalance.setPh(0f);
    	emp.addEmployeeLeaveBalance(newBalance);
    	
    	//when
    	employeeRepo.saveAndFlush(emp);
    	Optional<Employee> found = employeeRepo.findByEmailId("john.doe@nividous.com");
    	
    	//then
    	assertThat(found.isPresent()).isTrue();
    	assertThat(found.get().getLeaveBalance().getPh()).isEqualTo(0f);
    }
}
