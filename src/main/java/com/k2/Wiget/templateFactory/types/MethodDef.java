package com.k2.Wiget.templateFactory.types;
/**
 * An abstract defintion of a java method as required to generate the template assembly java source code
 * 
 * @author simon
 *
 */
public interface MethodDef {
	
	/**
	 * The method is a setter method for a field on the class
	 */
	public static final int SETTER_METHOD = 0;
	/**
	 * The method is a getter method for a field on the class
	 */
	public static final int GETTER_METHOD = 1;
	/**
	 * The method is a normal method (neither getter nor setter) on the class
	 */
	public static final int CLASS_METHOD = 2;
	
	/**
	 * @return	The type definition of the class defining the method
	 */
	public TypeDef getDeclaringTypeDef();
	/**
	 * @return The alias of the method. If this is a getter or setter method this will match the alias of the field to get or set as the preceeding 'get' from the method name
	 */
	public String getAlias();
	/**
	 * @return	The name of the method. This will be the same as the alias if the method is a CLASS_METHOD
	 */
	public String getName();
	/**
	 * @return	The type defintion of the methods return type
	 */
	public TypeDef getReturnTypeDef();
	/**
	 * @return	The method type. One of MethodDef.SETTER_METHOD, MethodDef.GETTER_METHOD or MethodDef.CLASS_METHOD
	 */
	public int getMethodType();
	


}
