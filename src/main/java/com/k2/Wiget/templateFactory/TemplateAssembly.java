package com.k2.Wiget.templateFactory;

import java.io.PrintWriter;

import com.k2.Wiget.Wiget;
import com.k2.Wiget.WigetAssembly;
import com.k2.Wiget.WigetFactory;

/**
 * The template assembly that is used to assemble template wigets
 * 
 * The indentation functionality is additional to the functionality required by the raw wiget assembly and are used by all template wigets
 * 
 * @author simon
 *
 * @param <W>	The type of the root wiget in the assembly
 * @param <T>	The required type of the root wiget in the assembly
 */
@SuppressWarnings("rawtypes")
public class TemplateAssembly<W extends Wiget,T> extends WigetAssembly<TemplateFamily,PrintWriter, W,T> {

	/**
	 * Create a template assembly for the given factory and root wiget type, using an indent string of '\t'
	 * @param factory	The TemplateFactory instance being used
	 * @param wigetType	The wiget type of the root assembly
	 */
	public TemplateAssembly(
			TemplateFactory factory, 
			Class<W> wigetType) {
		super(factory, wigetType);
		this.indentStr = "\t";
	}
	
	/**
	 * Create a template assembly for the given template factory, root wiget type and indent string
	 * @param factory	The TemplateFactory instance being used
	 * @param wigetType	The wiget type of the root assembly
	 * @param indentStr	The String to use for each indentation
	 */
	public TemplateAssembly(
			TemplateFactory factory, 
			Class<W> wigetType, String indentStr) {
		super(factory, wigetType);
		this.indentStr = indentStr;
	}
	
	private int indent = 0;
	private final String indentStr;
	private String currentIndent = "";
	
	/**
	 * Increase the current indent
	 */
	public void indent() { 
		indent++; 
		currentIndent = currentIndent+indentStr;
	}
	/**
	 * Decrease the current indent
	 */
	public void outdent() { 
		if (indent>0) {
			indent--;
			currentIndent = currentIndent.substring(0, currentIndent.length()-indentStr.length());
		} else {
			indent = 0;
			currentIndent = "";
		}
	}
	/**
	 * @return	The current indent as a string
	 */
	public String getIndent() { return currentIndent; }
	

}
