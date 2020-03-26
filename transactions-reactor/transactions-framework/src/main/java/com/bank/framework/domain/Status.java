package com.bank.framework.domain;

public enum Status {
	
	PENDING("PENDING"), SETTLED("SETTLED"), FUTURE("FUTURE"), INVALID("INVALID");

	private final String code;
	
	Status(final String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public static Status fromString(final String string){
        return Status.valueOf(string);
    }
}
