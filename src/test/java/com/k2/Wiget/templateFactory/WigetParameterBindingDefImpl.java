package com.k2.Wiget.templateFactory;

import java.util.ArrayList;
import java.util.List;

import com.k2.Util.classes.ClassUtil;
import com.k2.Wiget.templateFactory.types.ContainerDef;
import com.k2.Wiget.templateFactory.types.TemplateDef;
import com.k2.Wiget.templateFactory.types.TypeDef;
import com.k2.Wiget.templateFactory.types.WigetParameterBindingDef;

public class WigetParameterBindingDefImpl<V> implements WigetParameterBindingDef<V> {

	public static <V> WigetParameterBindingDefImpl<V> set(String targetParameterAlias, V value) {
		return new WigetParameterBindingDefImpl<V>(targetParameterAlias, value, null);
	}
	
	public static <V> WigetParameterBindingDefImpl<V> bind(String targetParameterAlias, String sourceParameterAlias) {
		return new WigetParameterBindingDefImpl<V>(targetParameterAlias, null, sourceParameterAlias);
	}
	
	private WigetParameterBindingDefImpl(String targetParameterAlias, V value, String sourceParameterAlias) {
		this.targetParameterAlias = targetParameterAlias;
		this.value = value;
		this.sourceParameterAlias = sourceParameterAlias;
	}
	

	@Override
	public boolean isLiteralBinding() { return (sourceParameterAlias == null); }

	private final String targetParameterAlias;
	@Override public String getTargetParameterAlias() { return targetParameterAlias; }
	
	private final V value;
	@Override
	public V getLiteralValue() { return value; }

	private final String sourceParameterAlias;
	@Override
	public String getSourceParameterAlias() { return sourceParameterAlias; }

	


}
