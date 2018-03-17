package com.k2.Wiget;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Expressions.Evaluator;

@SuppressWarnings("rawtypes")
public class BoundWigetParameter<W extends Wiget, V> extends WigetParameter<W, V> {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private WigetParameterEvaluator evaluator;
	private WigetParameter<W, V> sourceParameter;
 
	BoundWigetParameter(String alias, Field modelField, WigetParameter<W, V> sourceParameter, WigetParameterEvaluator evaluator) {
		super(alias, modelField);
		this.evaluator = evaluator;
		this.sourceParameter = sourceParameter;

		logger.trace("Created Evaluated wiget parameter {} for parameter {} on field {}:{} with evaluator{}", 
				alias, 
				sourceParameter.getAlias(), 
				modelField.getDeclaringClass().getName(), 
				modelField.getName(), 
				evaluator.getClass().getName());
	}

	@Override
	public V evaluate(Evaluator eval) {
		return sourceParameter.evaluate(evaluator);
	}
	
}
