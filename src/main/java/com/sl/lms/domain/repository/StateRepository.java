package com.sl.lms.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sl.lms.domain.State;

public interface StateRepository extends JpaRepository<State, Long>{
	List<State> findByCountryId(Long countryId);
}
