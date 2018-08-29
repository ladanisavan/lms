package com.sl.lms.domain.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.base.Strings;
import com.sl.lms.domain.Employee;
import com.sl.lms.util.DataTablesRequest;

public class EmployeeSpecsBuilder {

	Logger logger = LoggerFactory.getLogger(EmployeeSpecsBuilder.class);
	
	private final List<SearchCriteria> params;
	
	public EmployeeSpecsBuilder() {
		params = new ArrayList<SearchCriteria>();
	}
	
	public EmployeeSpecsBuilder with(DataTablesRequest dtRequest) {
		if(!Strings.isNullOrEmpty(dtRequest.getSearch().getValue())) {
			params.addAll(dtRequest.getColumns().stream()
					.filter(col -> col.isSearchable() == true)
					.map(col -> new SearchCriteria(col.getData(), ":", dtRequest.getSearch().getValue()))
					.collect(Collectors.toList()));
		}
		logger.debug("params : {}", params);
        return this;
    }
	
	public Specification<Employee> build() {
        if (params.size() == 0) {
            return null;
        }
 
        List<Specification<Employee>> specs = params.stream().map(param -> new EmployeeSpecification(param)).collect(Collectors.toList());
 
        Specification<Employee> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).or(specs.get(i));
        }
        return result;
    }
}
