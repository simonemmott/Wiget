package com.k2.Wiget.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The WigetImplementation annotation is used to mark a class as an implementation of a wiget.
 * 
 * It is used to identify the classes to be processed by the wiget factory when scanning a package for wigets.
 * 
 * @author simon
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface WigetImplementation {
	/**
	 * The name of the Wiget implementation
	 * The default value of this field is the name of the implementing wiget interface.
	 * 
	 * @return The name of the wiget being implemented
	 */
	public String name() default "";
}
