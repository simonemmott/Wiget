package com.k2.Wiget.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * The WigetSpecification annotation identifies interfaces that define a 	wiget.
 * 
 * @author simon
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface WigetSpecification {
	
	/**
	 * 
	 * @return	The name of the wiget being specified
	 * 
	 * Defaults to the simple name of the interface defining the wiget specification
	 */
	public String name() default "";
}
