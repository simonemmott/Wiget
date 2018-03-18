package com.k2.Wiget.templateFactory;

import java.util.ArrayList;
import java.util.List;

import com.k2.Util.classes.ClassUtil;
import com.k2.Wiget.AWiget;
import com.k2.Wiget.WigetAssembly;
import com.k2.Wiget.WigetFactory;
import com.k2.Wiget.WigetFamily;
import com.k2.Wiget.templateFactory.types.ContainerDef;
import com.k2.Wiget.templateFactory.types.FamilyDef;
import com.k2.Wiget.templateFactory.types.TemplateDef;
import com.k2.Wiget.templateFactory.types.TypeDef;

public class FamilyDefImpl implements FamilyDef {


	@SuppressWarnings("rawtypes")
	public FamilyDefImpl(Class<? extends WigetFamily> family) {
		this.name = family.getName();
	}
	public FamilyDefImpl(String name) {
		this.name = name;
	}
	private String name;
	@Override public String getName() { return name; }

	private String outputName;
	@Override public String getOutputName() { return outputName; }
	public FamilyDefImpl setOutput(Class<?> outputType) { this.outputName = outputType.getName(); return this; }

	private String assemblyName;
	@Override public String getAssemblyName() { return assemblyName; }
	@SuppressWarnings("rawtypes")
	public FamilyDefImpl setAssembly(Class<? extends WigetAssembly> assembly) { this.assemblyName = assembly.getName(); return this; }

	private String factoryName;
	@Override public String getFactoryName() { return factoryName; }
	@SuppressWarnings("rawtypes")
	public FamilyDefImpl setFactory(Class<? extends WigetFactory> factory) { this.factoryName = factory.getName(); return this; }

	private String abstractWigetName;
	@Override public String getAbstractWigetName() { return abstractWigetName; }
	@SuppressWarnings("rawtypes")
	public FamilyDefImpl setAbstractWiget(Class<? extends AWiget> abstractWiget) { this.abstractWigetName = abstractWiget.getName(); return this; }
	

}
