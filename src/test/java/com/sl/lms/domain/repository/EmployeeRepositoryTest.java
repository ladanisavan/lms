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
    	return emp;
    }
    
    @Test
    public void whenFindByEmpId_thenReturnEployee() {
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
    public void whenFindByEmailId_thenReturnEployee() {
    	//given
    	entityManager.persist(getNewEmpRecord());
    	entityManager.flush();
    	
    	//when
    	Optional<Employee> found = employeeRepo.findByEmailId("john.doe@nividous.com");
    	
    	//then
    	assertThat(found.isPresent()).isTrue();
    	assertThat(found.get().getFirstName()).isEqualTo("John");
    }
}
