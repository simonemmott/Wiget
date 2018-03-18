package com.k2.Wiget.templateFactory.types;

import java.util.List;

public interface AssembledWigetDef {
	
	public TypeDef getWigetDef();
	public List<WigetParameterBindingDef<?>> getParameterBindings();
	public List<WigetContainerBindingDef> getContainerBindings();

}
