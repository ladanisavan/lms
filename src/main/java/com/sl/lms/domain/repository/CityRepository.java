package com.sl.lms.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sl.lms.domain.City;

public interface CityRepository extends JpaRepository<City, Long>{
	List<City> findByStateId(Long stateId);
}
