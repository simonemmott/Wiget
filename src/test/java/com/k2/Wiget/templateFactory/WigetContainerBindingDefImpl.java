package com.k2.Wiget.templateFactory;

import java.util.ArrayList;
import java.util.List;

import com.k2.Util.classes.ClassUtil;
import com.k2.Wiget.templateFactory.types.AssembledWigetDef;
import com.k2.Wiget.templateFactory.types.ContainerDef;
import com.k2.Wiget.templateFactory.types.TemplateDef;
import com.k2.Wiget.templateFactory.types.TypeDef;
import com.k2.Wiget.templateFactory.types.WigetContainerBindingDef;
import com.k2.Wiget.templateFactory.types.WigetParameterBindingDef;

public class WigetContainerBindingDefImpl implements WigetContainerBindingDef {
	
	public static WigetContainerBindingDefImpl containedAssembly(String inContainerAlias, TypeDef containedWiget, String fromAssembledWigetParameterAlias) {
		return new WigetContainerBindingDefImpl(inContainerAlias, containedWiget, fromAssembledWigetParameterAlias, null);
	}

	public static WigetContainerBindingDefImpl boundContainer(String inContainerAlias, String fromTemplateContainerAlias) {
		return new WigetContainerBindingDefImpl(inContainerAlias, null, null, fromTemplateContainerAlias);
	}

	private WigetContainerBindingDefImpl(
			String inContainerAlias,
			TypeDef containedWiget,
			String fromAssembledWigetParameterAlias,
			String fromTemplateContainerAlias
			) {
		this.inContainerAlias = inContainerAlias;
		this.containedWiget = containedWiget;
		this.fromAssembledWigetParameterAlias = fromAssembledWigetParameterAlias;
		this.fromTemplateContainerAlias = fromTemplateContainerAlias;
		this.assembledWigetDef = (containedWiget != null) ? new AssembledWigetDefImpl(containedWiget).setContainedInContainerBinding(this) : null;
		
	}
	
	private final String inContainerAlias;
	@Override public String inContainerAlias() { return inContainerAlias; }

	private final TypeDef containedWiget;
	@Override public TypeDef containedWiget() { return containedWiget; }

	@Override
	public boolean isBoundToAssembledWiget() { return (containedWiget != null); }

	private final String fromAssembledWigetParameterAlias;
	@Override public String fromAssembledWigetParameterAlias() { return fromAssembledWigetParameterAlias; }

	private final String fromTemplateContainerAlias;
	@Override public String fromTemplateContainerAlias() { return fromTemplateContainerAlias; }

	private final AssembledWigetDefImpl assembledWigetDef;
	@Override
	public AssembledWigetDefImpl containedWigetAssembly() { return assembledWigetDef; }



}
