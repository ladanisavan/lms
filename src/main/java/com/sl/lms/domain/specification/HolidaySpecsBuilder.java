package com.sl.lms.domain.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.sl.lms.domain.Holiday;

public class HolidaySpecsBuilder {

	Logger logger = LoggerFactory.getLogger(HolidaySpecsBuilder.class);
	
	private final List<SearchCriteria> params;
	
	public HolidaySpecsBuilder() {
		params = new ArrayList<SearchCriteria>();
	}
	
	public HolidaySpecsBuilder with(Long officeId, String year, boolean optional) {
		if(officeId != -1) {
			params.add(new SearchCriteria("office.id", ":", officeId));
		}
		if(optional) {
			params.add(new SearchCriteria("optional", ":", false));
		}
		params.add(new SearchCriteria("holidayDate", "><", year));
		logger.debug("params : {}", params);
        return this;
    }
	
	public Specification<Holiday> build() {
        if (params.size() == 0) {
            return null;
        }
 
        List<Specification<Holiday>> specs = params.stream().map(param -> new HolidaySpecification(param)).collect(Collectors.toList());
 
        Specification<Holiday> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
