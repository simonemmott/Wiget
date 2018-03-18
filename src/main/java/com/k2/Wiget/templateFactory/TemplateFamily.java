package com.k2.Wiget.templateFactory;

import java.io.PrintWriter;

import com.k2.Wiget.WigetFamily;
import com.k2.Wiget.templateFactory.spec.TemplateImplementation;
import com.k2.Wiget.templateFactory.spec.TemplateSpecification;

public class TemplateFamily extends WigetFamily<PrintWriter> {

	public TemplateFamily() {
		super("TemplateFamily", PrintWriter.class, 
				TemplateSpecification.class,
				TemplateImplementation.class);
	}

}
