package com.k2.Wiget.templateFactory;

import com.k2.Util.classes.ClassUtil;
import com.k2.Wiget.templateFactory.types.MethodDef;
import com.k2.Wiget.templateFactory.types.TypeDef;

public class MethodDefImpl implements MethodDef {

	private String name;
	TypeDef declaringTypeDef;
	private TypeDef returnTypeDef;
	private int methodType;
	
	public MethodDefImpl(String name, TypeDef returnTypeDef, int methodType) {
		this.name = name;
		this.returnTypeDef = returnTypeDef;
		this.methodType = methodType;
		
	}
	@Override public TypeDef getDeclaringTypeDef() { return declaringTypeDef; }

	@Override public String getAlias() { return ClassUtil.getAliasFromMethodName(name); }

	@Override public String getName() { return name; }

	@Override public TypeDef getReturnTypeDef() { return returnTypeDef; }

	@Override
	public int getMethodType() { return methodType; }

}
