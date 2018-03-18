package com.k2.Wiget.templateFactory;

import java.util.ArrayList;
import java.util.List;

import com.k2.Util.classes.ClassUtil;
import com.k2.Wiget.AWiget;
import com.k2.Wiget.Wiget;
import com.k2.Wiget.templateFactory.types.AssembledWigetDef;
import com.k2.Wiget.templateFactory.types.ContainerDef;
import com.k2.Wiget.templateFactory.types.FamilyDef;
import com.k2.Wiget.templateFactory.types.TemplateDef;
import com.k2.Wiget.templateFactory.types.TypeDef;

public class TemplateDefImpl implements TemplateDef {

	private String name;
	private String implementationName;
	public TemplateDefImpl(Class<?> wigetInterface, Class<?> wigetImplementation) {
		this.name = wigetInterface.getName();
		this.implementationName = wigetImplementation.getName();
	}
	public TemplateDefImpl(String name, String implementationName) {
		this.name = name;
		this.implementationName = implementationName;
	}
	@Override public String getName() { return name; }
	@Override public String getImplementationName() { return implementationName; }

	private FamilyDef familyDef;
	@Override public FamilyDef getFamilyDef() { return familyDef; }
	public TemplateDefImpl setFamilyDef(FamilyDef familyDef) { this.familyDef = familyDef; return this; }

	private TypeDef extendsWigetInterface;
	@Override public TypeDef getExtendsWigetInterface() { return extendsWigetInterface; }
	public TemplateDefImpl setExtendsWigetInterface(TypeDef extendsWigetInterface) { this.extendsWigetInterface = extendsWigetInterface; return this; }

	private TypeDef requiresType;
	@Override public TypeDef getRequiresTypeDef() { return requiresType; }
	public TemplateDefImpl setRequiresType(TypeDef requiresType) { this.requiresType = requiresType; return this; }

	private List<ContainerDef> containerDefs = new ArrayList<ContainerDef>();
	@Override public List<ContainerDef> getContainerDefs() { return containerDefs; }
	public TemplateDefImpl add(ContainerDef containerDef) {
		ContainerDefImpl cdi = (ContainerDefImpl)containerDef;
		cdi.template = this;
		containerDefs.add(containerDef);
		return this;
	}

	private AssembledWigetDef assemblesWiget;
	@Override public AssembledWigetDef getAssemblesWiget() { return assemblesWiget; }
	public TemplateDefImpl setAssemblesWiget(AssembledWigetDef assemblesWiget) { this.assemblesWiget = assemblesWiget; return this; }


}
