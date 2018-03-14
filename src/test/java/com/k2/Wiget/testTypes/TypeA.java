package com.k2.Wiget.testTypes;

public class TypeA {

	private int number;
	public int getNumber() { return number; }
	private String alias;
	public String getAlias() { return alias; }
	private String title;
	public String getTitle() { return title; }
	
	public TypeA(int number, String alias, String title) {
		this.number = number;
		this.alias = alias;
		this.title = title;
	}
	
}
