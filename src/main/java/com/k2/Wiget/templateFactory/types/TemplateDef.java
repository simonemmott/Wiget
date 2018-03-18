package com.k2.Wiget.templateFactory.types;

import java.util.List;

public interface TemplateDef {
	
	public FamilyDef getFamilyDef();
	public String getName();
	public String getImplementationName();
	public TypeDef getExtendsWigetInterface();
	public AssembledWigetDef getAssemblesWiget();
	public TypeDef getRequiresTypeDef();
	public List<ContainerDef> getContainerDefs();

}
