package com.k2.Wiget.templateFactory;

import java.io.PrintWriter;

import com.k2.Wiget.Wiget;
import com.k2.Wiget.WigetFactory;

/**
 * This is the extension of the raw wiget factory for the TemplateFamily wigets
 * @author simon
 *
 */
public class TemplateFactory extends WigetFactory<TemplateFamily, PrintWriter> {

	/**
	 * Create a Template factory drawing template wiget assemblies from the given packages
	 * 
	 * @param packageNames	The packages and thier sub packages to scan for template wiget assemblies
	 */
	public TemplateFactory(String ... packageNames) {
		super(new TemplateFamily(), PrintWriter.class, packageNames);
	}

	/**
	 * Create a TemplateAssembly with the giver wiget type as the root wiget of the assembly
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <W extends Wiget,T> TemplateAssembly<W,T> getAssembly(Class<W> wigetType) {
		return (TemplateAssembly<W,T>) super.getAssembly(TemplateAssembly.class, wigetType);
	}

}
