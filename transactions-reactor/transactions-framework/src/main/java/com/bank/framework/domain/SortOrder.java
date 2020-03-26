package com.bank.framework.domain;

public enum SortOrder {

	ASC("ASC"), DESC("DESC");
	
	private final String order;
	
	SortOrder(final String order) {
		this.order = order;
	}
	
	public String getOrder() {
		return order;
	}

	public static SortOrder fromString(final String order) {
		return SortOrder.valueOf(order);
	}
}
