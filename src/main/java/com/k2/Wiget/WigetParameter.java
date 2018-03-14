package com.k2.Wiget;

import java.lang.reflect.Field;

import com.k2.Expressions.expression.K2Expression;
import com.k2.Expressions.expression.K2ParameterExpression;

@SuppressWarnings("rawtypes")
public abstract class WigetParameter<W extends Wiget, V> extends K2ParameterExpression<V> implements K2Expression<V>{
		
	private final Field modelField;
	public Field getModelField() { return modelField; }
	
	@SuppressWarnings("unchecked")
	WigetParameter(String alias, Field modelField) {
		super((Class<V>) modelField.getType(), alias);
		this.modelField = modelField;
	}

	

}
