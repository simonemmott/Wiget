package com.k2.Wiget;

import com.k2.Expressions.Evaluator;
import com.k2.Wiget.Wiget;

@SuppressWarnings("rawtypes")
public interface WigetParameterEvaluator<W extends Wiget> extends Evaluator {

	public Object getDataSource();

}
