package com.k2.Wiget.templateFactory.impl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.k2.Wiget.annotation.WigetImplementation;
import com.k2.Wiget.templateFactory.ATemplateWiget;
import com.k2.Wiget.templateFactory.TypeDefImpl;
import com.k2.Wiget.templateFactory.TemplateAssembly;
import com.k2.Wiget.templateFactory.TemplateFamily;
import com.k2.Wiget.templateFactory.spec.TemplateSpecification;
import com.k2.Wiget.templateFactory.types.ContainerDef;
import com.k2.Wiget.templateFactory.types.MethodDef;
import com.k2.Wiget.templateFactory.types.TemplateDef;
import com.k2.Wiget.templateFactory.types.TypeDef;
import com.k2.Util.classes.ClassUtil;
import com.k2.Wiget.AssembledWiget;
import com.k2.Wiget.Wiget;

@WigetImplementation
public class TemplateSpecificationImpl extends ATemplateWiget<TemplateDef> implements TemplateSpecification{
	
	@SuppressWarnings("rawtypes")
	@Override
	public PrintWriter output(
			AssembledWiget<TemplateFamily, PrintWriter, ? extends Wiget, TemplateDef> a,
			PrintWriter out) {
		
		TemplateAssembly ta = (TemplateAssembly)a.assembly();
		
		String templateName = ClassUtil.getBasenameFromCanonicalName(a.get(TemplateSpecification.model.name));
		
		TypeDef requiresTypeDef = a.get(TemplateSpecification.model.requiresTypeDef);
		Set<TypeDef> dependencies = new TreeSet<TypeDef>();
		
		dependencies.add(a.get(TemplateSpecification.model.extendsWigetInterface));
		dependencies.add(a.get(TemplateSpecification.model.requiresTypeDef));
		
		List<MethodDef> getterMethods = new ArrayList<MethodDef>();
		for (MethodDef md : requiresTypeDef.getMethodDefs()) {
			if (md.getMethodType() == MethodDef.GETTER_METHOD) {
				getterMethods.add(md);
				TypeDef returnType = md.getReturnTypeDef();
				dependencies.add(returnType);
				if (returnType.isCollection()) {
					dependencies.add(returnType.collectionOf());
				}
			}
		}
		
		dependencies.add(new TypeDefImpl("com.k2.Wiget.annotation.WigetSpecification"));
		dependencies.add(new TypeDefImpl("com.k2.Wiget.WigetContainer"));
		dependencies.add(new TypeDefImpl("com.k2.Wiget.WigetParameter"));
		
		
		out.println("package "+ClassUtil.getPackageNameFromCanonicalName(a.get(TemplateSpecification.model.name))+";");
		out.println("");
		for (TypeDef d : dependencies) 
			out.println("import "+d.getName()+";");
		out.println("");
		out.println("@WigetSpecification()");
		out.println("public interface "+templateName+" extends "+a.get(TemplateSpecification.model.extendsWigetInterface).getBaseName()+"<"+a.get(TemplateSpecification.model.requiresTypeDef).getBaseName()+"> {");
		out.println("");
		ta.indent();
		out.println(ta.getIndent()+"public static class Model {");
		ta.indent();
		for(MethodDef md : getterMethods) {
			if (md.getReturnTypeDef().isCollection())
				out.println(ta.getIndent()+"public WigetParameter<"+templateName+", "+md.getReturnTypeDef().getBaseName()+"<"+md.getReturnTypeDef().collectionOf().getBaseName()+">> "+md.getAlias()+";");
			else 
				out.println(ta.getIndent()+"public WigetParameter<"+templateName+", "+md.getReturnTypeDef().getBaseName()+"> "+md.getAlias()+";");
		}
		for(ContainerDef cd : a.get(TemplateSpecification.model.containerDefs)) {
			out.println(ta.getIndent()+"public WigetContainer<"+templateName+"> "+cd.getAlias()+";");
		}
		ta.outdent();
		out.println(ta.getIndent()+"}");
		out.println("");
		out.println(ta.getIndent()+"public static final Model model = new Model();");
		out.println(ta.getIndent()+"@Override public default Object staticModel() { return model; }");
		out.println(ta.getIndent()+"@Override public default Class<?> modelType() { return Model.class; }");
		out.println("");
		ta.outdent();
		out.println(ta.getIndent()+"}");
		
		return out;
	}




	
	

}
