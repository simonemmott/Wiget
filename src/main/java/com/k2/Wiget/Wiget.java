package com.k2.Wiget;

public interface Wiget<F extends WigetFamily<O>,O,T> {

	public String alias();
	public Class<T> requiresType();
	public abstract Class<?> modelType();
	public abstract Object generateModel();
	public abstract Object staticModel();
	@SuppressWarnings("rawtypes")
	public O output(AssembledWiget<F,O,? extends Wiget, T> assembly, O out);

}
