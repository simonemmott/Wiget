package com.k2.Wiget.templateFactory.types;

import java.util.List;

import com.k2.Wiget.templateFactory.TypeDefImpl;

public interface TypeDef {
	
	public static final TypeDef voidDef = new TypeDefImpl("void");
	
	public static final TypeDef intDef = new TypeDefImpl("int");
		
	public static final TypeDef longDef = new TypeDefImpl("long");
		
	public static final TypeDef floatDef = new TypeDefImpl("float");
		
	public static final TypeDef dooubleDef = new TypeDefImpl("double");
		
	public static final TypeDef booleanDef = new TypeDefImpl("boolean");
							
	public static final TypeDef shortDef = new TypeDefImpl("short");
							
	public static final TypeDef byteDef = new TypeDefImpl("byte");
							
	public static final TypeDef charDef = new TypeDefImpl("char");
	
	public static final TypeDef IntegerDef = new TypeDefImpl("java.lang.Integer");
	public static final TypeDef LongDef = new TypeDefImpl("java.lang.Long");
	public static final TypeDef FloatDef = new TypeDefImpl("java.lang.Float");
	public static final TypeDef DoubleDef = new TypeDefImpl("java.lang.Double");
	public static final TypeDef StringDef = new TypeDefImpl("java.lang.String");
	public static final TypeDef BooleanDef = new TypeDefImpl("java.lang.Boolean");
	
							
	public String getPackageName();
	public String getBaseName();
	public String getName();
	public List<FieldDef> getFieldDefs();
	public List<MethodDef> getMethodDefs();
	public boolean isCollection();
	public TypeDef collectionOf();

}
