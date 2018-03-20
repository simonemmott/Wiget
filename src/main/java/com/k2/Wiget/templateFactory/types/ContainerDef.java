package com.k2.Wiget.templateFactory.types;

/**
 * An abstract defintion of a container as required to generate the template assembly java source code
 * 
 * @author simon
 *
 */
public interface ContainerDef {

	/**
	 * @return	The template for which this is a container
	 */
	public TemplateDef getTemplateDef();
	/**
	 * @return	The alias of this container
	 */
	public String getAlias();
	
}
