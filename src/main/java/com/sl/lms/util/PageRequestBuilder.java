package com.sl.lms.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.Assert;

import com.sl.lms.util.DataTablesRequest.Order;

public class PageRequestBuilder {

	private DataTablesRequest dtRequest;
	private int page;
	private int size;
	private Sort sort;

	public PageRequestBuilder(DataTablesRequest dtRequest) {
		this.dtRequest = dtRequest;
	}
	
	public PageRequestBuilder withPage() {
		Assert.isTrue(dtRequest.getLength()!=0, "length can not be zero!");
		page = dtRequest.getStart()/dtRequest.getLength();
		size = dtRequest.getLength();
		return this;
	}
	
	public PageRequestBuilder withOrder() {
		Assert.isTrue(dtRequest.getOrders()!=null, "Invalid order info!");
		Order order = dtRequest.getOrders().get(0);
		Direction dir = Sort.Direction.ASC;
		if(order.getDir().equals("desc")) {
			dir = Sort.Direction.DESC;
		}
		sort = new Sort(dir, dtRequest.getColumns().get(order.getColumn()).getData());
		return this;
	}
	
	public PageRequest build() {
		return PageRequest.of(page, size, sort);
	}
}
