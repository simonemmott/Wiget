package com.k2.Wiget;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Expressions.Evaluator;

/**
 * A mapped wiget parameter is a wiget pameter that has been mapped to a specific field of the wigets static model and a perticular getter method of the wigets typed data source.
 * @author simon
 *
 * @param <W>	The wiget fro which this is a parameter
 * @param <V>	The data type provided by the parameter
 */
@SuppressWarnings("rawtypes")
public class MappedWigetParameter<W extends Wiget, V> extends WigetParameter<W, V> {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final Method getterMethod;
	/**
	 * @return The getter method of the requires type that supplies values for this parameter
	 */
	public Method getterMethod() { return getterMethod; }
 
	/**
	 * Create am instance of a mapped wiget parameter mapping the given getter method to the parameter identified by the given alias and contined in the given field of the wigets static model
	 * @param alias			The parameter alias
	 * @param modelField		The field of the static model that represents this parameter
	 * @param getterMethod	The getter method of the requires type of the wiget that extracts from the wiget data source the value of this parameter
	 */
	MappedWigetParameter(String alias, Field modelField, Method getterMethod) {
		super(alias, modelField);
		this.getterMethod = getterMethod;

		logger.trace("Created Mapped wiget parameter {} for field {}:{} getting method {}::{}()", alias, modelField.getDeclaringClass().getName(), modelField.getName(), getterMethod.getDeclaringClass().getName(), getterMethod.getName());
	}

	/**
	 * Extract the value provided by the wigets typed data source through the getter method associated to this mapped wiget parameter and return it or return null if the wigets typed data source is not set
	 */
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
