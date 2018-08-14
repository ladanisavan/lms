package com.sl.lms.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DataTablesRequest {
	private int draw;

	private int start;

	private int length;

	private Search search;

	@JsonProperty("order")
	private List<Order> orders;

	private List<Column> columns;

	@Data
	public static class Search {
		private String value;
		private boolean regex;
	}

	@Data
	public static class Order {
		private int column;
		private String dir;
	}

	@Data
	public static class Column {
		private String data;
		private String name;
		private boolean searchable;
		private boolean orderable;
		private Search search;
		private boolean regex;
	}
}