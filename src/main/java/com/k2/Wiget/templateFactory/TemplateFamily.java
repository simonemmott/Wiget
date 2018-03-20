package com.k2.Wiget.templateFactory;

import java.io.PrintWriter;

import com.k2.Wiget.WigetFamily;
import com.k2.Wiget.templateFactory.spec.TemplateImplementation;
import com.k2.Wiget.templateFactory.spec.TemplateSpecification;

/**
 * The TemplateFammily for the template factory defines the required wigets to create template assemblies
 * 
 * @author simon
 *
 */
public class TemplateFamily extends WigetFamily<PrintWriter> {

	/**
	 * Create a template family specifying the TemplateSpecification and TemplateImplementation as required wigets
	 */
	public TemplateFamily() {
		super("TemplateFamily", PrintWriter.class, 
				TemplateSpecification.class,
				TemplateImplementation.class);
	}

}
