package com.k2.Wiget;

import java.util.Collection;
import java.util.List;

import com.k2.Expressions.expression.CurrentTime;
import com.k2.Expressions.expression.K2ParameterExpression;
import com.k2.Expressions.predicate.K2Predicate;
import com.k2.Wiget.testTypes.TypeA;
import com.k2.Wiget.testWigets.TestWigetAssembly;
import com.k2.Wiget.testWigets.spec.TestWigetA;
import com.k2.Wiget.testWigets.spec.TestWigetC;

@SuppressWarnings("rawtypes")
public class AssembledWiget<F extends WigetFamily<O>,O,W extends Wiget,T> implements WigetParameterEvaluator<W> {
	
	private final WigetAssembly<F,O,W,?> assembly;
	public WigetAssembly<F,O,W,?> assembly() { return assembly; }

	private final W wiget;
	
	private final Object model;
	
	private T typedDataSource;
	
	private final AssembledWiget<F,O,?,?> parent;
	
	private K2Predicate includeCriteria;
	private void setIncludeCriteria(K2Predicate includeCriteria) { this.includeCriteria = includeCriteria; }
	
	private WigetParameter<?,?> valueFrom;
	
	
	@SuppressWarnings("unchecked")
	AssembledWiget(WigetAssembly<F,O,W,?> assembly, Class<W> wigetType) {
		this.assembly = assembly;
		this.wiget = (W) assembly.factory().getWiget(wigetType);
		this.model = wiget.generateModel();
		this.parent = null;
	}

	@SuppressWarnings("unchecked")
	AssembledWiget(WigetAssembly<F,O,W,?> assembly, AssembledWiget<F,O,?,?> parent, Class<W> wigetType, WigetParameter<?,?> valueFrom) {
		this.assembly = assembly;
		this.parent = parent;
		this.wiget = (W) assembly.factory().getWiget(wigetType);
		this.model = wiget.generateModel();
		this.valueFrom = valueFrom;
	}
	
	@SuppressWarnings("unchecked")
	public O outputContents(WigetContainer container, O out) {
		
		List<AssembledWiget> contents = getContents(container);
		for (AssembledWiget aw : contents) {
			Object dataSource = get(aw.valueFrom);
			out = (O) aw.output(dataSource, out);
		}
		return out;
	}

	public O output(Object dataSource, O out) {

		if (parent != null && includeCriteria != null) {
			if ( ! includeCriteria.evaluate(parent))
				return out;
		}
		
		if (dataSource instanceof Collection) {
			for (Object source : (Collection)dataSource) {
				out = outputNoCollection(source, out);
			}
		} else {
			out = outputNoCollection(dataSource, out);
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	private O outputNoCollection(Object dataSource, O out) {
		
		if (wiget.requiresType().isAssignableFrom(dataSource.getClass())) {
			return typedOutput((T)dataSource, out);
		}
		
		if (assembly.factory().getAdapterFactory() != null)
			if (assembly.factory().getAdapterFactory().hasAdapter(dataSource.getClass(), wiget.requiresType()))
				return typedOutput((T) assembly.factory().getAdapterFactory().adapt(dataSource, wiget.requiresType()), out);
		
		
		throw new WigetError("The recieved data source of type {} is not of the required type {} and no adapter exists to convert it.", dataSource.getClass().getName(), wiget.requiresType().getName());
	}
	
	@SuppressWarnings("unchecked")
	private O typedOutput(T typedDataSource, O out) {
		this.typedDataSource = typedDataSource;
		return (O) wiget.output(this, out);
	}

	@SuppressWarnings("unchecked")
	public <V> V get(WigetParameter<?, V> parameter) {
		try {
			WigetParameter<W, V> wp = (WigetParameter<W, V>) parameter.getModelField().get(model);
			return wp.evaluate(this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new WigetError("Unable to get value of paramter {} from Wiget model {} - {}", e, parameter.getAlias(), model.getClass().getName(), e.getMessage());
		}
	}
	
	private List<AssembledWiget> getContents(WigetContainer<W> container) {
		return get(container).getContents();
	}
	
	@SuppressWarnings("unchecked")
	private WigetContainer<W> get(WigetContainer<W> container) {
		try {
			return (WigetContainer<W>) container.getModelField().get(model);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new WigetError("Unable to get model container for container {} in Wiget model {} - {}", e, container.getAlias(), model.getClass().getName(), e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public <V> AssembledWiget<F,O,W,T> set(WigetParameter<? extends W, V> parameter, V value) {
		
		try {
			WigetParameter<W, V> wp = (WigetParameter<W, V>) parameter.getModelField().get(model);
			if (wp instanceof ValuedWigetParameter) {
				ValuedWigetParameter vwp = (ValuedWigetParameter)wp;
				vwp.setValue(value);
			} else {
				ValuedWigetParameter vwp = new ValuedWigetParameter(parameter.getAlias(), parameter.getModelField(), value);
				parameter.getModelField().set(model, vwp);
			}
			return this;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new WigetError("Unable to set value of paramter {} from Wiget model {} - {}", e, parameter.getAlias(), model.getClass().getName(), e.getMessage());
		}
		
	}


	@Override
	public void add(K2ParameterExpression<?> expr) {
		// Nothing to do, wiget parameters cannot be changed.
		if ( ! (expr instanceof WigetParameter))
			throw new WigetError("Unable to add non wiget parameters to a wiget assembly");
	}

	@Override
	public boolean checkParametersSet(K2Predicate... predicates) {
		for (K2Predicate p : predicates) {
			p.populateParameters(this);
		}
		return true;
	}

	/**
	 * This field holds a reference to an instance of current time.
	 * This field is populated and returned on the first call to getCurrentTime() for this instance and then returned on 
	 * subsequently calls to get current time.
	 * 
	 * Consequently calls to get current time for a particular instance of SimpleParameterEvaluator will always return the same time
	 * This ensures that comparisons between times will always compare to the same value of current time for the same evaluator
	 */
	private CurrentTime currentTime;

	@Override
	public CurrentTime getCurrentTime() {
		if (currentTime == null) currentTime = new CurrentTime();
		return currentTime;
	}
	
	@Override
	public Object getDataSource() {
		return typedDataSource;
	}

	@SuppressWarnings("unchecked")
	public <Q extends Wiget> AssembledWiget<F,O,Q,?> add(WigetContainer<?> container, Class<Q> containedWigetType, WigetParameter<?, ?> sourceParameter) {
		AssembledWiget aw = new AssembledWiget(assembly, this, containedWigetType, sourceParameter);
		return get((WigetContainer<W>)container).add(aw);
	}
	
	@SuppressWarnings("unchecked")
	public <Q extends Wiget> AssembledWiget<F,O,Q,?> addIf(K2Predicate includeCriteria, WigetContainer<?> container, Class<Q> containedWigetType, WigetParameter<?, ?> sourceParameter) {
		AssembledWiget aw = new AssembledWiget(assembly, this, containedWigetType, sourceParameter);
		aw.setIncludeCriteria(includeCriteria);
		return get((WigetContainer<W>)container).add(aw);
	}
	
	public AssembledWiget<F,O,?,?> up() { return parent; }
	
	public AssembledWiget<F,O,?,?> root() { return assembly.root(); }
 

}
