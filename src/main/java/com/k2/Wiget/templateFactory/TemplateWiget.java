package com.k2.Wiget.templateFactory;

import java.io.PrintWriter;

import com.k2.Wiget.Wiget;

/**
 * This interface ensures that all template wigets are part of the TemplateFamily and use a PrintWriter for output
 * @author simon
 *
 * @param <T>	The requires type of the wiget
 */
public interface TemplateWiget<T> extends Wiget<TemplateFamily, PrintWriter, T> {

	public default Class<?> modelType() { return null; }

}
