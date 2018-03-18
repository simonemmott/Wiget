package com.k2.Wiget.templateFactory.types;

public interface WigetParameterBindingDef<V> {

	public boolean isLiteralBinding();
	public String getTargetParameterAlias();
	public V getLiteralValue();
	public String getSourceParameterAlias();
	
}
