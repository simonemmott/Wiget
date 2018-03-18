package com.k2.Wiget.templateFactory;

import java.util.ArrayList;
import java.util.List;

import com.k2.Util.classes.ClassUtil;
import com.k2.Wiget.templateFactory.types.AssembledWigetDef;
import com.k2.Wiget.templateFactory.types.ContainerDef;
import com.k2.Wiget.templateFactory.types.FamilyDef;
import com.k2.Wiget.templateFactory.types.TemplateDef;
import com.k2.Wiget.templateFactory.types.TypeDef;
import com.k2.Wiget.templateFactory.types.WigetContainerBindingDef;
import com.k2.Wiget.templateFactory.types.WigetParameterBindingDef;

public class AssembledWigetDefImpl implements AssembledWigetDef {
	
	private TypeDef wigetDef;
	
	public AssembledWigetDefImpl(TypeDef wigetDef) {
		this.wigetDef = wigetDef;
	}

	@Override
	public TypeDef getWigetDef() { return wigetDef; }

	private List<WigetParameterBindingDef<?>> parameterBindings = new ArrayList<WigetParameterBindingDef<?>>();
	@Override
	public List<WigetParameterBindingDef<?>> getParameterBindings() { return parameterBindings; }
	public AssembledWigetDefImpl add(WigetParameterBindingDef<?> parameterBinding) { 
		parameterBindings.add(parameterBinding);
		return this;
	}

	private List<WigetContainerBindingDef> containerBindings = new ArrayList<WigetContainerBindingDef>();
	@Override
	public List<WigetContainerBindingDef> getContainerBindings() { return containerBindings; }
	public AssembledWigetDefImpl add(WigetContainerBindingDef containerBinding) { 
		containerBindings.add(containerBinding);
		return this;
	}

	WigetContainerBindingDefImpl containedInContainerBinding;
	public WigetContainerBindingDefImpl up() { return containedInContainerBinding; }
	AssembledWigetDefImpl setContainedInContainerBinding(WigetContainerBindingDefImpl containedInContainerBinding) { 
		this.containedInContainerBinding = containedInContainerBinding; 
		return this;
	}


}
