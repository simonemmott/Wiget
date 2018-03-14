package com.k2.Wiget;

import java.util.HashSet;
import java.util.Set;

public abstract class WigetFamily<O> {

	private final Class<? extends Wiget<? extends WigetFamily<O>,O,?>>[] requires;
	public Set<Class<? extends Wiget<? extends WigetFamily<O>,O,?>>> requiredWigets() {
		Set<Class<? extends Wiget<? extends WigetFamily<O>,O,?>>> set = new HashSet<Class<? extends Wiget<? extends WigetFamily<O>,O,?>>>();
		for (Class<? extends Wiget<? extends WigetFamily<O>,O,?>> required : requires)
			set.add(required);
		return set;
	}
	private final String alias;
	public final String alias() { return alias; }
	
	private final Class<O> outputType;
	public final Class<O> outputType() { return outputType; }
	
	@SafeVarargs
	public WigetFamily(String alias, Class<O> outputType, Class<? extends Wiget<? extends WigetFamily<O>,O,?>> ... requires) {
		this.alias = alias;
		this.outputType = outputType;
		this.requires = requires;
	}


}
