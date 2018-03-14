package com.k2.Wiget.testTypes;

public class TypeB {

	private Long longNumber;
	public Long getLongNumber() { return longNumber; }
	private String name;
	public String getName() { return name; }
	private String description;
	public String getDescription() { return description; }
	
	public TypeB(Long longNumber, String name, String description) {
		this.longNumber = longNumber;
		this.name = name;
		this.description = description;
	}
	
}
