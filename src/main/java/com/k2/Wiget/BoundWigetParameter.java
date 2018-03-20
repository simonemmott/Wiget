package com.k2.Wiget;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Expressions.Evaluator;

/**
 * Bound wiget parameters are wiget parameters that have been bound to the value of a parameter of another (ancestor) assembled wiget. Typically this is 
 * the assembled wiget that is assembling a template assembly containing this parameters wiget
 * @author simon
 *
 * @param <W>	The wiget for which this is a parameter
 * @param <V>	The datatype of this wiget parameter
 */
@SuppressWarnings("rawtypes")
public class BoundWigetParameter<W extends Wiget, V> extends WigetParameter<W, V> {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private WigetParameterEvaluator evaluator;
	private WigetParameter<W, V> sourceParameter;
 
	/**
	 * Creaate a bound wiget parameter that is bound to the given source parameter which will be evaluated using the given evaluator.
	 * @param alias				The alias of the wiget to be bound
	 * @param modelField			The field on the static model that represents the parameter
	 * @param sourceParameter	The parameter that will provide a value for this parameter when this parameter is evaluated
	 * @param evaluator			The evaluator that will be used to evaluate the source parameter 
	 */
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

	/**
	 * NOTE - This method evaluates the value of this parameter using the parameter and evaluator with which it was created.
	 */
	@Override
	public V evaluate(Evaluator eval) {
		return sourceParameter.evaluate(evaluator);
	}
	
}
