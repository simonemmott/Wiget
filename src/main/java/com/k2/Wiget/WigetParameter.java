package com.k2.Wiget;

import java.lang.reflect.Field;

import com.k2.Expressions.expression.K2Expression;
import com.k2.Expressions.expression.K2ParameterExpression;

/**
 * This abstract class is the base class of all wiget parameters implementations
 * 
 * Wiget parameters extend the K2ParameterExpression class and implement the K2Expression interface and as such
 * cam be used in expression and predicate definitions with a suitable evaluator the WigetParameterEvaluator
 * 
 * @author simon
 *
 * @param <W>	The wiget for which this is a parameter
 * @param <V>	The data type supplied by this parameter
 */
@SuppressWarnings("rawtypes")
public abstract class WigetParameter<W extends Wiget, V> extends K2ParameterExpression<V> implements K2Expression<V>{
		
	private final Field modelField;
	/**
	 * @return	The field from the wigets static model that represents this wiget parameter
	 */
	public Field getModelField() { return modelField; }
	
	/**
	 * Create an instance of the wiget parameter for the given parameter alias and static model field
	 * @param alias			The alias of the wiget parameter
	 * @param modelField		The field of the wigets static model that represents this wiget parameter
	 */
	@SuppressWarnings("unchecked")
	WigetParameter(String alias, Field modelField) {
		super((Class<V>) modelField.getType(), alias);
		this.modelField = modelField;
	}

	

}
