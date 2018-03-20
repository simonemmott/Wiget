package com.k2.Wiget.templateFactory.types;

/**
 * The defintion of a wiget parameter binding 
 * 
 * @author simon
 *
 * @param <V>	The data type of the bound parameter
 */
public interface WigetParameterBindingDef<V> {

	/**
	 * @return	True if this binding binds a literal value to the parameter
	 */
	public boolean isLiteralBinding();
	/**
	 * @return	The alias of the parameter whose value is being set
	 */
	public String getTargetParameterAlias();
	/**
	 * @return	If this binding binds a literal value to the parameter this is the literal value to bind
	 */
	public V getLiteralValue();
	/**
	 * @return	If this is NOT binding a literal value to the parameter then this is the alias of the template parameter bound to the parameter being bound
	 */
	public String getSourceParameterAlias();
	
}
