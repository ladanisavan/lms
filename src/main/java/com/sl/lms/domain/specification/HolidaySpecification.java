package com.sl.lms.domain.specification;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sl.lms.domain.Holiday;

public class HolidaySpecification implements Specification<Holiday> {

	private final SearchCriteria criteria;

	public HolidaySpecification(SearchCriteria param) {
		this.criteria = param;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Predicate toPredicate(Root<Holiday> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		if (criteria.getOperation().equalsIgnoreCase(">")) {
			return builder.greaterThanOrEqualTo(getPath(root, criteria.getKey()), criteria.getValue().toString());
		} else if (criteria.getOperation().equalsIgnoreCase("<")) {
			return builder.lessThanOrEqualTo(getPath(root, criteria.getKey()), criteria.getValue().toString());
		} else if (criteria.getOperation().equalsIgnoreCase("><")) {
			return builder.between(getPath(root, criteria.getKey()),
					new GregorianCalendar(Integer.valueOf(criteria.getValue().toString()), 00, 01).getTime(),
					new GregorianCalendar(Integer.valueOf(criteria.getValue().toString()), 11, 31).getTime());
		} else if (criteria.getOperation().equalsIgnoreCase(":")) {
			if (getPath(root, criteria.getKey()).getJavaType() == String.class) {
				return builder.like(getPath(root, criteria.getKey()), "%" + criteria.getValue() + "%");
			} else {
				return builder.equal(getPath(root, criteria.getKey()), criteria.getValue());
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private Path getPath(Root<Holiday> root, String key) {
		Path path = null;
		for (String str : criteria.getKey().split("\\.")) {
			if (path == null) {
				path = root.get(str);
			} else {
				path = path.get(str);
			}
		}
		return path;
	}
}
