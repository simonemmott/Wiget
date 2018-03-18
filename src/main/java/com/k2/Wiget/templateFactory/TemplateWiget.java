package com.k2.Wiget.templateFactory;

import java.io.PrintWriter;

import com.k2.Wiget.Wiget;

public interface TemplateWiget<T> extends Wiget<TemplateFamily, PrintWriter, T> {

	public default Class<?> modelType() { return null; }

}
