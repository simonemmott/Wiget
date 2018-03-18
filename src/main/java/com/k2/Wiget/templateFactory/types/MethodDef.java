package com.k2.Wiget.templateFactory.types;

public interface MethodDef {
	
	public static final int SETTER_METHOD = 0;
	public static final int GETTER_METHOD = 1;
	public static final int CLASS_METHOD = 2;
	
	public TypeDef getDeclaringTypeDef();
	public String getAlias();
	public String getName();
	public TypeDef getReturnTypeDef();
	public int getMethodType();
	


}
