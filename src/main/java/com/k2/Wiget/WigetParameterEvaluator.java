package com.k2.Wiget;

import com.k2.Expressions.Evaluator;
import com.k2.Wiget.Wiget;

/**
 * This interface identifies those classes that can be used to evaluate a wiget parameter in the contect of an Expression or Predicate
 * @author simon
 *
 * @param <W>	The wiget for which this evaluator is providing values
 */
@SuppressWarnings("rawtypes")
public interface WigetParameterEvaluator<W extends Wiget> extends Evaluator {

	/**
	 * @return	The data source object that contains values for wiget parameters
	 */
	public Object getDataSource();

}
