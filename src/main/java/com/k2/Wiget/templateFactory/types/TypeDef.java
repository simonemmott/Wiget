package com.k2.Wiget.templateFactory.types;

import java.util.List;

//import com.k2.Wiget.templateFactory.TypeDefImpl;
import com.k2.Wiget.templateFactory.impl.SimpleTypeDef;

/**
 * The defintion of a data type
 * @author simon
 *
 */
public interface TypeDef {
	/**
	 * The void primitive type
	 */
	public static final TypeDef voidDef = new SimpleTypeDef("void");
	/**
	 * the int primitive type
	 */
	public static final TypeDef intDef = new SimpleTypeDef("int");
	/**
	 * The long primitive type	
	 */
	public static final TypeDef longDef = new SimpleTypeDef("long");
	/**
	 * The float primitive type	
	 */
	public static final TypeDef floatDef = new SimpleTypeDef("float");
	/**
	 * The double primitive type
	 */
	public static final TypeDef doubleDef = new SimpleTypeDef("double");
	/**
	 * The boolean primitive type	
	 */
	public static final TypeDef booleanDef = new SimpleTypeDef("boolean");
	/**
	 * The short primitive type
	 */
	public static final TypeDef shortDef = new SimpleTypeDef("short");
	/**
	 * The byte primitive type
	 */
	public static final TypeDef byteDef = new SimpleTypeDef("byte");
	/**
	 * The char primitive type						
	 */
	public static final TypeDef charDef = new SimpleTypeDef("char");
	/**
	 * The Integer native type
	 */
	public static final TypeDef IntegerDef = new SimpleTypeDef("java.lang.Integer");
	/**
	 * The Long native type
	 */
	public static final TypeDef LongDef = new SimpleTypeDef("java.lang.Long");
	/**
	 * The Float native type
	 */
	public static final TypeDef FloatDef = new SimpleTypeDef("java.lang.Float");
	/**
	 * The Double native type
	 */
	public static final TypeDef DoubleDef = new SimpleTypeDef("java.lang.Double");
	/**
	 * The String native type
	 */
	public static final TypeDef StringDef = new SimpleTypeDef("java.lang.String");
	/**
	 * The Boolean native type
	 */
	public static final TypeDef BooleanDef = new SimpleTypeDef("java.lang.Boolean");
	/**
	 * The Character native type
	 */
	public static final TypeDef CharacterDef = new SimpleTypeDef("java.lang.Character");
	/**
	 * The Byte native type
	 */
	public static final TypeDef ByteDef = new SimpleTypeDef("java.lang.Byte");
	/**
	 * The Short native type
	 */
	public static final TypeDef ShortDef = new SimpleTypeDef("java.lang.Short");
	
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
