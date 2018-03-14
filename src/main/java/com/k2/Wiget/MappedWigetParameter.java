package com.k2.Wiget;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Expressions.Evaluator;

@SuppressWarnings("rawtypes")
public class MappedWigetParameter<W extends Wiget, V> extends WigetParameter<W, V> {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final Method getterMethod;
	public Method getterMethod() { return getterMethod; }
 
	MappedWigetParameter(String alias, Field modelField, Method getterMethod) {
		super(alias, modelField);
		this.getterMethod = getterMethod;

		logger.trace("Created Mapped wiget parameter {} for field {}:{} getting method {}::{}()", alias, modelField.getDeclaringClass().getName(), modelField.getName(), getterMethod.getDeclaringClass().getName(), getterMethod.getName());
	}

	@SuppressWarnings("unchecked")
	@Override
	public V evaluate(Evaluator eval) {
		if (eval instanceof WigetParameterEvaluator) {
			WigetParameterEvaluator wpe = (WigetParameterEvaluator)eval;
			Object dataSource = wpe.getDataSource();
			if (dataSource==null)
				return null;
			try {
				return (V) getterMethod.invoke(wpe.getDataSource());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new WigetError("Unable to extract value from data source {}::{}() - {}", e, dataSource.getClass().getName(), getterMethod.getName(), e.getMessage());
			}
		}
			
		return null;
	}
	
}
