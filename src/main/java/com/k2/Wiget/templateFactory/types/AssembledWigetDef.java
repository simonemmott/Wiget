package com.k2.Wiget.templateFactory.types;

import java.util.List;
/**
 * An abstract defintion of an assembled wiget as required to generate the template assembly java source code
 * 
 * @author simon
 *
 */
public interface AssembledWigetDef {
	
	/**
	 * @return	The type definition of the wiget being assembled
	 */
	public TypeDef getWigetDef();
	/**
	 * @return	The list of the parameter bindings supplying values for the wiget in this assembly
	 */
	public List<WigetParameterBindingDef<?>> getParameterBindings();
	/**
	 * @return	The list of container bindings supplying the contents of the containers of this wiget assembly
	 */
	public List<WigetContainerBindingDef> getContainerBindings();

}
