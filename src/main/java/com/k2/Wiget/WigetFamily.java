package com.k2.Wiget;

import java.util.HashSet;
import java.util.Set;

/**
 * A wiget family groups together wigets that are designed to work together and as such in the absence of other restrictions all wigets within a family can be
 * contained with other wigets of the same family, however they cannot be contained and cannot contain wigets from another family.
 * 
 * Wiget families also define a list of required wiget implementation.  This list guarantees a minimum set of wigets that must be implemented for a wiget factory to be created
 * for the wiget family.
 * 
 * @author simon
 *
 * @param <O>	The output type of the wigets in the family
 */
public abstract class WigetFamily<O> {

	private final Class<? extends Wiget<? extends WigetFamily<O>,O,?>>[] requires;
	/**
	 * @return	The set of wigets that must be implemented for a factory to be created for this wiget family
	 */
	public Set<Class<? extends Wiget<? extends WigetFamily<O>,O,?>>> requiredWigets() {
		Set<Class<? extends Wiget<? extends WigetFamily<O>,O,?>>> set = new HashSet<Class<? extends Wiget<? extends WigetFamily<O>,O,?>>>();
		for (Class<? extends Wiget<? extends WigetFamily<O>,O,?>> required : requires)
			set.add(required);
		return set;
	}
	private final String alias;
	/**
	 * @return	The alis of this wiget family
	 */
	public final String alias() { return alias; }
	
	private final Class<O> outputType;
	/**
	 * @return	The output type of all the wigets in this family
	 */
	public final Class<O> outputType() { return outputType; }
	
	/**
	 * Create an instance of a wiget family specifying the alias, output type and the array of wiget specifications (interfaces)
	 * that are required by this wiget family
	 * @param alias			The wiget family alias
	 * @param outputType		The output type of all wigets in the family
	 * @param requires		The array of wiget specifications that must be implemented in order for this wiget family to used to create a wiget factory.
	 */
	@SafeVarargs
	public WigetFamily(String alias, Class<O> outputType, Class<? extends Wiget<? extends WigetFamily<O>,O,?>> ... requires) {
		this.alias = alias;
		this.outputType = outputType;
		this.requires = requires;
	}


}
