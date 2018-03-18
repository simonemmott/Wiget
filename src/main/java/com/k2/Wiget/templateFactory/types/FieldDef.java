package com.k2.Wiget.templateFactory.types;

public interface FieldDef {
	
	public TypeDef getDeclaringTypeDef();
	public String getAlias();
	public TypeDef getJavaTypeDef();
	public MethodDef getGetterMethodDef();
	public MethodDef getSetterMethodDef();
}
