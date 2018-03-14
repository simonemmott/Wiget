package com.k2.Wiget.testTypes;

import java.util.ArrayList;
import java.util.List;

public class TypeC {

	private String alias;
	public String getAlias() { return alias; }
	private String name;
	public String getName() { return name; }
	private String description;
	public String getDescription() { return description; }
	private TypeA a;
	public void setA(TypeA a) { this.a = a; }
	public TypeA getA() { return a; }
	private List<TypeB> bs;
	public void addB(TypeB b) {
		if (bs == null)
			bs = new ArrayList<TypeB>();
		bs.add(b);
	}
	public void setBs(List<TypeB> bs) { this.bs = bs; }
	public List<TypeB> getBs() { return bs; }
	
	public TypeC(String alias, String name, String description) {
		this.alias = alias;
		this.name = name;
		this.description = description;
	}
	
}
