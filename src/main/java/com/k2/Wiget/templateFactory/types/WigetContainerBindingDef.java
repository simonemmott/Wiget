package com.k2.Wiget.templateFactory.types;

/**
 * Wiget container bindings define a wiget contained in a container of a wiget assembly or bind a container of the template to a container of an assembled wiget
 *  
 * @author simon
 *
 */
public interface WigetContainerBindingDef {

	/**
	 * @return	The alias of the container in which the wiget is contained or to which the template container is bound
	 */
	public String inContainerAlias();
	/**
	 * @return	If the container binding is binding an assembled wiget then this is the type defining the contained wiget
	 */
	public TypeDef containedWiget();
	/**
	 * @return	True if this container binding is binding an assembled wiget
	 */
	public boolean isBoundToAssembledWiget();
	/**
	 * @return	If this container binding is binding an assembled wiget then this is the alias of the parameter on the parent
	 * assembled wiget that supplies the data source value for the contained wiget. If this parameter returns a collection
	 * then the wiget is repeated for each element in the collection
	 */
	public String fromAssembledWigetParameterAlias();
	/**
	 * @return	If this container binding is NOT binding an assembled wiget then this is the alias of the container of the template whose contents
	 * are bound to the target container
	 */
	public String fromTemplateContainerAlias();
	/**
	 * @return	The defintion of the assembled wiget contained if this binding in binding an assembled wiget
	 */
	public AssembledWigetDef containedWigetAssembly();
	
}
