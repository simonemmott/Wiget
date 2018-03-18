package com.k2.Wiget.templateFactory.types;

public interface WigetContainerBindingDef {

	public String inContainerAlias();
	public TypeDef containedWiget();
	public boolean isBoundToAssembledWiget();
	public String fromAssembledWigetParameterAlias();
	public String fromTemplateContainerAlias();
	public AssembledWigetDef containedWigetAssembly();
	
}
