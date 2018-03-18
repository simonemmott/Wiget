package com.k2.Wiget.templateFactory;

import java.io.PrintWriter;

import com.k2.Wiget.Wiget;
import com.k2.Wiget.WigetFactory;

public class TemplateFactory extends WigetFactory<TemplateFamily, PrintWriter> {

	public TemplateFactory(String ... packageNames) {
		super(new TemplateFamily(), PrintWriter.class, packageNames);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <W extends Wiget,T> TemplateAssembly<W,T> getAssembly(Class<W> wigetType) {
		return (TemplateAssembly<W,T>) super.getAssembly(TemplateAssembly.class, wigetType);
	}

}
