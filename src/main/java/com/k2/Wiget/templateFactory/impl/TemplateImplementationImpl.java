package com.k2.Wiget.templateFactory.impl;

import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

import com.k2.Wiget.annotation.WigetImplementation;
import com.k2.Wiget.templateFactory.ATemplateWiget;
//import com.k2.Wiget.templateFactory.TypeDefImpl;
import com.k2.Wiget.templateFactory.TemplateAssembly;
import com.k2.Wiget.templateFactory.TemplateFamily;
import com.k2.Wiget.templateFactory.spec.TemplateImplementation;
import com.k2.Wiget.templateFactory.types.AssembledWigetDef;
import com.k2.Wiget.templateFactory.types.FamilyDef;
import com.k2.Wiget.templateFactory.types.TemplateDef;
import com.k2.Wiget.templateFactory.types.TypeDef;
import com.k2.Wiget.templateFactory.types.WigetContainerBindingDef;
import com.k2.Wiget.templateFactory.types.WigetParameterBindingDef;
import com.k2.Util.classes.ClassUtil;
import com.k2.Util.java.JavaUtil;
import com.k2.Wiget.AssembledWiget;
import com.k2.Wiget.Wiget;
/**
 * This wiget implementation generates the java source code for wiget implementation of template assemblies
 * 
 * @author simon
 *
 */
@WigetImplementation
public class TemplateImplementationImpl extends ATemplateWiget<TemplateDef> implements TemplateImplementation{
	
	@SuppressWarnings("rawtypes")
	@Override
	public PrintWriter output(
			AssembledWiget<TemplateFamily, PrintWriter, ? extends Wiget, TemplateDef> a,
			PrintWriter out) {
		
		TemplateAssembly ta = (TemplateAssembly)a.assembly();
		
		String implementationName = ClassUtil.getBasenameFromCanonicalName(a.get(TemplateImplementation.model.implementationName));
		String templateName = ClassUtil.getBasenameFromCanonicalName(a.get(TemplateImplementation.model.name));
		
		FamilyDef familyDef = a.get(TemplateImplementation.model.familyDef);
		String familyBasename = ClassUtil.getBasenameFromCanonicalName(familyDef.getName());
		String outputBasename = ClassUtil.getBasenameFromCanonicalName(familyDef.getOutputName());
		String extendsAbstractWigetBasename = ClassUtil.getBasenameFromCanonicalName(familyDef.getAbstractWigetName());
		String factoryBasename = ClassUtil.getBasenameFromCanonicalName(familyDef.getFactoryName());
		String assemblyBasename = ClassUtil.getBasenameFromCanonicalName(familyDef.getAssemblyName());
		
		TypeDef requiresTypeDef = a.get(TemplateImplementation.model.requiresTypeDef);

		AssembledWigetDef assemblesWiget = a.get(TemplateImplementation.model.assemblesWiget);
		
		Set<TypeDef> dependencies = new TreeSet<TypeDef>();
		
		dependencies.add(new SimpleTypeDef(familyDef.getOutputName()));
		dependencies.add(new SimpleTypeDef("com.k2.Wiget.annotation.WigetImplementation"));
		dependencies.add(new SimpleTypeDef(a.get(TemplateImplementation.model.name)));
		dependencies.add(new SimpleTypeDef("com.k2.Wiget.AssembledWiget"));
		dependencies.add(new SimpleTypeDef("com.k2.Wiget.Wiget"));
		dependencies.add(new SimpleTypeDef(familyDef.getAssemblyName()));
		dependencies.add(new SimpleTypeDef(familyDef.getFactoryName()));
		dependencies.add(new SimpleTypeDef(familyDef.getName()));
		dependencies.add(new SimpleTypeDef(familyDef.getAbstractWigetName()));
		dependencies.add(a.get(TemplateImplementation.model.requiresTypeDef));

		addContainedWigetsToDependencies(dependencies, assemblesWiget);
		
		out.println("package "+ClassUtil.getPackageNameFromCanonicalName(a.get(TemplateImplementation.model.implementationName))+";");
		out.println("");
		for (TypeDef d : dependencies) 
			out.println("import "+d.getName()+";");
		out.println("");
		out.println("@WigetImplementation");
		out.println("public class "+implementationName+" extends "+extendsAbstractWigetBasename+"<"+requiresTypeDef.getBaseName()+"> implements "+templateName+"{");

		ta.indent();
		out.println(ta.getIndent()+"@SuppressWarnings(\"rawtypes\")");
		out.println(ta.getIndent()+"@Override");
		out.println(ta.getIndent()+"public "+outputBasename+" output(");
		ta.indent();
		ta.indent();
		out.println(ta.getIndent()+"AssembledWiget<"+familyBasename+", "+outputBasename+", ? extends Wiget, "+requiresTypeDef.getBaseName()+"> a,");
		out.println(ta.getIndent()+outputBasename+" out) {");		
		ta.outdent();
		out.println(ta.getIndent()+"");
		out.println(ta.getIndent()+factoryBasename+" factory = ("+factoryBasename+") a.assembly().factory();");		
		out.println(ta.getIndent()+"");
		out.println(ta.getIndent()+assemblyBasename+"<"+assemblesWiget.getWigetDef().getBaseName()+", "+requiresTypeDef.getBaseName()+"> templateAssembly = factory.getAssembly("+assemblesWiget.getWigetDef().getBaseName()+".class);");
		out.println(ta.getIndent()+"templateAssembly.root()");
		ta.indent();
		out = outputAssembledWiget(templateName, ta, assemblesWiget, out);
		out.println(ta.getIndent()+";");
		ta.outdent();
		out.println(ta.getIndent()+"");
		out.println(ta.getIndent()+"return templateAssembly.output(a.getDataSource(), out);");
		ta.outdent();
		out.println(ta.getIndent()+"}");
		ta.outdent();
		out.println("}");
		
		return out;
	}

	
	private void addContainedWigetsToDependencies(Set<TypeDef> dependencies, AssembledWigetDef assembledWiget) {
		dependencies.add(assembledWiget.getWigetDef());
		for (WigetContainerBindingDef containerBinding : assembledWiget.getContainerBindings())
			if (containerBinding.isBoundToAssembledWiget())
				addContainedWigetsToDependencies(dependencies, containerBinding.containedWigetAssembly());
	}

	@SuppressWarnings("rawtypes")
	private PrintWriter outputAssembledWiget(String templateName, TemplateAssembly ta, AssembledWigetDef assembledWiget, PrintWriter out) {
		
		for(WigetParameterBindingDef parameterBinding : assembledWiget.getParameterBindings()) {
			if (parameterBinding.isLiteralBinding()) {
				out.println(ta.getIndent()+".set("+assembledWiget.getWigetDef().getBaseName()+".model."+parameterBinding.getTargetParameterAlias()+", "+JavaUtil.toJavaSource(parameterBinding.getLiteralValue())+")");
			} else {
				out.println(ta.getIndent()+".bind("+assembledWiget.getWigetDef().getBaseName()+".model."+parameterBinding.getTargetParameterAlias()+", a, "+templateName+".model."+parameterBinding.getSourceParameterAlias()+")");
			}
		}
		for (WigetContainerBindingDef containerBinding : assembledWiget.getContainerBindings()) {
			if (containerBinding.isBoundToAssembledWiget()) {
				out.println(ta.getIndent()+".add("+assembledWiget.getWigetDef().getBaseName()+".model."+containerBinding.inContainerAlias()+", "+
					containerBinding.containedWiget().getBaseName()+".class, "+
					assembledWiget.getWigetDef().getBaseName()+".model."+containerBinding.fromAssembledWigetParameterAlias()+")");
				ta.indent();
				out = outputAssembledWiget(templateName, ta, containerBinding.containedWigetAssembly(), out);
				ta.outdent();
			} else {
				out.println(ta.getIndent()+".bind("+assembledWiget.getWigetDef().getBaseName()+".model."+containerBinding.inContainerAlias()+", a, "+
						templateName+".model."+containerBinding.fromTemplateContainerAlias()+")");
			}
		}
		out.println(ta.getIndent()+".up()");
		return out;
	}

	
	

}
