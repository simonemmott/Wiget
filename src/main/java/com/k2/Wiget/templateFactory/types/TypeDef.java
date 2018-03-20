package com.k2.Wiget.templateFactory.types;

import java.util.List;

import com.k2.Wiget.templateFactory.TypeDefImpl;

/**
 * The defintion of a data type
 * @author simon
 *
 */
public interface TypeDef {
	/**
	 * The void primitive type
	 */
	public static final TypeDef voidDef = new TypeDefImpl("void");
	/**
	 * the int primitive type
	 */
	public static final TypeDef intDef = new TypeDefImpl("int");
	/**
	 * The long primitive type	
	 */
	public static final TypeDef longDef = new TypeDefImpl("long");
	/**
	 * The float primitive type	
	 */
	public static final TypeDef floatDef = new TypeDefImpl("float");
	/**
	 * The double primitive type
	 */
	public static final TypeDef doubleDef = new TypeDefImpl("double");
	/**
	 * The boolean primitive type	
	 */
	public static final TypeDef booleanDef = new TypeDefImpl("boolean");
	/**
	 * The short primitive type
	 */
	public static final TypeDef shortDef = new TypeDefImpl("short");
	/**
	 * The byte primitive type
	 */
	public static final TypeDef byteDef = new TypeDefImpl("byte");
	/**
	 * The char primitive type						
	 */
	public static final TypeDef charDef = new TypeDefImpl("char");
	/**
	 * The Integer native type
	 */
	public static final TypeDef IntegerDef = new TypeDefImpl("java.lang.Integer");
	/**
	 * The Long native type
	 */
	public static final TypeDef LongDef = new TypeDefImpl("java.lang.Long");
	/**
	 * The Float native type
	 */
	public static final TypeDef FloatDef = new TypeDefImpl("java.lang.Float");
	/**
	 * The Double native type
	 */
	public static final TypeDef DoubleDef = new TypeDefImpl("java.lang.Double");
	/**
	 * The String native type
	 */
	public static final TypeDef StringDef = new TypeDefImpl("java.lang.String");
	/**
	 * The Boolean native type
	 */
	public static final TypeDef BooleanDef = new TypeDefImpl("java.lang.Boolean");
	/**
	 * The Character native type
	 */
	public static final TypeDef CharacterDef = new TypeDefImpl("java.lang.Character");
	/**
	 * The Byte native type
	 */
	public static final TypeDef ByteDef = new TypeDefImpl("java.lang.Byte");
	/**
	 * The Short native type
	 */
	public static final TypeDef ShortDef = new TypeDefImpl("java.lang.Short");
	
	/**						
	 * @return	The package name of the type
	 */
	public String getPackageName();
	/**
	 * @return	The simple name of the type
	 */
	public String getBaseName();
	/**
	 * @return	The full class name of the type
	 */
	public String getName();
	/**
	 * @return	The list of fields defined on the type
	 */
	public List<FieldDef> getFieldDefs();
	/**
	 * @return	The methods defined on the type
	 */
	public List<MethodDef> getMethodDefs();
	/**
	 * @return	True if the type is a collection
	 */
	public boolean isCollection();
	/**
	 * @return	The Type in the collection if the type is a collection
	 */
	public TypeDef collectionOf();

}
