package com.k2.Wiget;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Expressions.Evaluator;

@SuppressWarnings("rawtypes")
public class ValuedWigetParameter<W extends Wiget, V> extends WigetParameter<W, V> {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private V value;
	public V getValue() { return value; }
	public void setValue(V value) { this.value = value; }
 
	ValuedWigetParameter(String alias, Field modelField, V value) {
		super(alias, modelField);
		this.value = value;

		logger.trace("Created Value wiget parameter {} for field {}:{}", alias, modelField.getDeclaringClass().getName(), modelField.getName());
	}

	@Override
	public V evaluate(Evaluator eval) {
		return value;
	}
	
}
