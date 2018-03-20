package com.k2.Wiget;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Expressions.Evaluator;

/**
 * The ValuedWigetParameter provides the value for a wiget parameter from a fixed literal value given when the ValuedWigetParameter is created
 * 
 * @author simon
 *
 * @param <W>	The wiget for which this is a parameter
 * @param <V>	The data type of this wiget parameter
 */
@SuppressWarnings("rawtypes")
public class ValuedWigetParameter<W extends Wiget, V> extends WigetParameter<W, V> {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private V value;
	/**
	 * Set the value of this ValuedWigetParameter
	 * @param value	The new value for this valued wiget parameter
	 */
	void setValue(V value) { this.value = value; }
 
	/**
	 * Create an instance of ValuedWigetParameter for the given alias, static field and literal value
	 * @param alias			The alias of the parameter that is being given a fixed literal value
	 * @param modelField		The field of the static model that represents this paraemter
	 * @param value			The literal value of the identified parameter
	 */
	ValuedWigetParameter(String alias, Field modelField, V value) {
		super(alias, modelField);
		this.value = value;

		logger.trace("Created Value wiget parameter {} for field {}:{}", alias, modelField.getDeclaringClass().getName(), modelField.getName());
	}

	/**
	 * Return the literal value of this parameter without recourse to the given evaluator
	 */
	@Override
	public V evaluate(Evaluator eval) {
		return value;
	}
	
}
