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
public interface TemplateSpecification extends TemplateWiget<TemplateDef> {
	
	public static class Model{
		public WigetParameter<TemplateSpecification, FamilyDef> familyDef;
		public WigetParameter<TemplateSpecification, String> name;
		public WigetParameter<TemplateSpecification, String> implementationName;
		public WigetParameter<TemplateSpecification, TypeDef> extendsWigetInterface;
		public WigetParameter<TemplateSpecification, AssembledWigetDef> assemblesWiget;
		public WigetParameter<TemplateSpecification, TypeDef> requiresTypeDef;
		public WigetParameter<TemplateSpecification, List<ContainerDef>> containerDefs;
	}
	public static final Model model = new Model();
	@Override public default Object staticModel() { return model; }
	@Override public default Class<?> modelType() { return Model.class; }
	
}
