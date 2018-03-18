package com.k2.Wiget.templateFactory;

import java.io.PrintWriter;

import com.k2.Wiget.Wiget;
import com.k2.Wiget.WigetAssembly;
import com.k2.Wiget.WigetFactory;

@SuppressWarnings("rawtypes")
public class TemplateAssembly<W extends Wiget,T> extends WigetAssembly<TemplateFamily,PrintWriter, W,T> {

	public TemplateAssembly(
			WigetFactory<TemplateFamily, PrintWriter> factory, 
			Class<W> wigetType) {
		super(factory, wigetType);
		this.indentStr = "\t";
	}
	
	public TemplateAssembly(
			WigetFactory<TemplateFamily, PrintWriter> factory, 
			Class<W> wigetType, String indentStr) {
		super(factory, wigetType);
		this.indentStr = indentStr;
	}
	
	private int indent = 0;
	private final String indentStr;
	private String currentIndent = "";
	public String getIndentStr() { return indentStr; }
	public void indent() { 
		indent++; 
		currentIndent = currentIndent+indentStr;
	}
	public void outdent() { 
		if (indent>0) {
			indent--;
			currentIndent = currentIndent.substring(0, currentIndent.length()-indentStr.length());
		} else {
			indent = 0;
			currentIndent = "";
		}
	}
	public String getIndent() { return currentIndent; }
	

}
