package com.k2.Wiget.templateFactory.types;

/**
 * An abstract defintion of a wiget family as required to generate the template assembly java source code
 * 
 * @author simon
 *
 */
public interface FamilyDef {

	/**
	 * @return	The full class name of the class that represents this wiget family
	 */
	public String getName();
	/**
	 * @return	The full class name of the output type of all wigets in this family
	 */
	public String getOutputName();
	/**
	 * @return	The full class name of the wiget assembly class for this family of wigets
	 */
	public String getAssemblyName();
	/**
	 * @return	The full class name of the wiget factory for this wiget family
	 */
	public String getFactoryName();
	/**
	 * @return	The full class name of the abstract wiget used by wigets in this family
	 */
	public String getAbstractWigetName();
}
