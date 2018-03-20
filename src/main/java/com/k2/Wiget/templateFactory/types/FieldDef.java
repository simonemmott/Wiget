package com.k2.Wiget.templateFactory.types;

/**
 * An abstract defintion of a field as required to generate the template assembly java source code
 * 
 * @author simon
 *
 */
public interface FieldDef {
	
	/**
	 * @return	The type defintion of the declaring type of this field
	 */
	public TypeDef getDeclaringTypeDef();
	/**
	 * @return	The alias (name) of this field
	 */
	public String getAlias();
	/**
	 * @return	The type definition of the java type of this field
	 */
	public TypeDef getJavaTypeDef();
	/**
	 * @return	The method definition of the getter method of this field if there is one
	 */
	public MethodDef getGetterMethodDef();
	/**
	 * @return	The method definition of the setter method of this field if there is one
	 */
	public MethodDef getSetterMethodDef();
}
