package com.bank.framework.domain;

public enum Channel {

	CLIENT("CLIENT"), ATM("ATM"), INTERNAL("INTERNAL");
	
	private final String code;
	
	Channel(final String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public static Channel fromString(final String string){
        return Channel.valueOf(string);
    }
}
