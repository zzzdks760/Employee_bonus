package dev.bonus.model;

public enum Grade {
	A("A"),
	B("B"),
	C("C"),
	D("D");

	private final String value;
	
	Grade(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
