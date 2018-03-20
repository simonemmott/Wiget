package com.k2.Wiget.templateFactory.types;

import java.util.List;

/**
 * An abstract defintion of a template assembly as required to generate the template assembly java source code
 * 
 * @author simon
 *
 */
public interface TemplateDef {
	
	/**
	 * @return  The family that is used to generate the template
	 */
	public FamilyDef getFamilyDef();
	/**
	 * @return The full class name of the template specification class
	 */
	public String getName();
	/**
	 * @return	The full class name of the template implementation class
	 */
	public String getImplementationName();
	/**
	 * @return	The type defintion of the abstract wiget that is extended for this template
	 */
	public TypeDef getExtendsWigetInterface();
	/**
	 * @return	The assembled wiget definition of the root assembled wiget
	 */
	public AssembledWigetDef getAssemblesWiget();
	/**
	 * @return	The type definition of the wigets requires type
	 */
	public TypeDef getRequiresTypeDef();
	/**
	 * @return	The list of container definitions
	 */
	public List<ContainerDef> getContainerDefs();

}
