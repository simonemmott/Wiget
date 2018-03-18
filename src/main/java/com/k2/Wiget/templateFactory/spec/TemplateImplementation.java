package com.k2.Wiget.templateFactory.spec;

import com.k2.Wiget.annotation.WigetSpecification;
import com.k2.Wiget.templateFactory.TemplateWiget;
import com.k2.Wiget.templateFactory.types.AssembledWigetDef;
import com.k2.Wiget.templateFactory.types.ContainerDef;
import com.k2.Wiget.templateFactory.types.FamilyDef;
import com.k2.Wiget.templateFactory.types.TemplateDef;
import com.k2.Wiget.templateFactory.types.TypeDef;

import java.util.List;

import com.k2.Wiget.WigetParameter;

@WigetSpecification
public interface TemplateImplementation extends TemplateWiget<TemplateDef> {
	
	public static class Model{
		public WigetParameter<TemplateImplementation, FamilyDef> familyDef;
		public WigetParameter<TemplateImplementation, String> name;
		public WigetParameter<TemplateImplementation, String> implementationName;
		public WigetParameter<TemplateImplementation, TypeDef> extendsWigetInterface;
		public WigetParameter<TemplateImplementation, AssembledWigetDef> assemblesWiget;
		public WigetParameter<TemplateImplementation, TypeDef> requiresTypeDef;
		public WigetParameter<TemplateImplementation, List<ContainerDef>> containerDefs;
	}
	public static final Model model = new Model();
	@Override public default Object staticModel() { return model; }
	@Override public default Class<?> modelType() { return Model.class; }
	
}
