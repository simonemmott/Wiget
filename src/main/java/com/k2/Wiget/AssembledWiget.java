package com.k2.Wiget;

import java.util.Collection;
import java.util.List;

import com.k2.Expressions.expression.CurrentTime;
import com.k2.Expressions.expression.K2ParameterExpression;
import com.k2.Expressions.predicate.K2Predicate;

/**
 * Am assembled wiget represents a wiget within an assembly of wigets
 * 
 * Assembled wigets can set values for the wigets parameters and if set they will override 
 * the parameter values derived from the wigets typed data source.
 * 
 * They also define the contents wigets containers
 * 
 * @author simon
 *
 * @param <F>	The widget family
 * @param <O>	The output type
 * @param <W>	The wiget being assembled
 * @param <T>	The type of data required by the wiget being assembled
 */
@SuppressWarnings("rawtypes")
public class AssembledWiget<F extends WigetFamily<O>,O,W extends Wiget,T> implements WigetParameterEvaluator<W>, ContainedWiget<O> {
	
	/**
	 * The assembly that contains this assembled wiget
	 */
	private final WigetAssembly<F,O,W,?> assembly;
	
	/**
	 * 
	 * @return The assembly that contains this assembled wiget
	 */
	public WigetAssembly<F,O,W,?> assembly() { return assembly; }

	/**
	 * The wiget being assembled
	 */
	private final W wiget;
	
	/**
	 * The model is set to an instance of the wiget model and is used to extract values from the typed data source or hold the set parameter values
	 */
	private final Object model;
	
	/**
	 * This object holds the parameter source data for parameters that have not been set on the assembly
	 */
	private T typedDataSource;
	
	/**
	 * The assembled wiget that contains this assembled wiget within on of its wiget containers
	 */
	private final AssembledWiget<F,O,?,?> parent;
	
	/**
	 * The predicate is evaluated to identify whether the assembled wiget is to be output when the parent wiget is output
	 */
	private K2Predicate includeCriteria;
	
	/**
	 * Set the predicate that defines whether  or not this assembled wiget should be output
	 * @param includeCriteria	The predicate that controls whether this assembled wiget will be output
	 */
	private void setIncludeCriteria(K2Predicate includeCriteria) { this.includeCriteria = includeCriteria; }
	
	/**
	 * The wiget parameter of the parent assembled wiget that will be evaluated to extract the value that is usewd as the data source for this assembled wiget.
	 */
	private WigetParameter<?,?> valueFrom;
	
	/**
	 * 
	 * @return	The wiget parameter of the parent wiget in the assembly that provides the data source for this assembled wiget.
	 */
	public WigetParameter<?,?> getFromParameter() { return valueFrom; }
	
	/**
	 * Create an assembled wiget as the root assembled wiget within an assembly of wigets
	 * @param assembly		The wiget assembly for which this will be the root wiget.
	 * @param wigetType		The wiget that this assembled wiget assembles
	 */
	@SuppressWarnings("unchecked")
	AssembledWiget(WigetAssembly<F,O,W,?> assembly, Class<W> wigetType) {
		this.assembly = assembly;
		this.wiget = (W) assembly.factory().getWiget(wigetType);
		this.model = wiget.generateModel();
		this.parent = null;
	}

	/**
	 * Create an assembled wiget as a child of another assembled wiget.
	 * @param assembly		The assembley containting this assembled wiget
	 * @param parent			The assembled wiget that contains this assembled wiget within the given assembly
	 * @param wigetType		The type of wiget being assembled
	 * @param valueFrom		The wiget parameter of the parent wiget in the assembly that provides the data source for this assembled wiget
	 */
	@SuppressWarnings("unchecked")
	AssembledWiget(WigetAssembly<F,O,W,?> assembly, AssembledWiget<F,O,?,?> parent, Class<W> wigetType, WigetParameter<?,?> valueFrom) {
		this.assembly = assembly;
		this.parent = parent;
		this.wiget = (W) assembly.factory().getWiget(wigetType);
		this.model = wiget.generateModel();
		this.valueFrom = valueFrom;
	}
	
	/**
	 * Output the contents of the given container of this assembled wiget in the order in which the contents were added to the assembled wiget container
	 * @param container		The container for which to output the contents
	 * @param out			The instance of the output type in which the contents of the container are to be output
	 * @return				The instance of the output type in which the contents of the container have been output after they have been output
	 */
	@SuppressWarnings("unchecked")
	public O outputContents(WigetContainer container, O out) {
		
		List<ContainedWiget<O>> contents = getContents(container);
		// For each item contained in this assembled wiget container 
		for (ContainedWiget<O> cw : contents) {
			// If the item is an assembled wiget output it using the object provided by this assembled wiget through the valueFrom parameter of the
			// contained assembled wiget as the contained assembled wigets data source
			if (cw instanceof AssembledWiget) {
				AssembledWiget<F,O,?,?> aw = (AssembledWiget)cw;
				Object dataSource = get(aw.valueFrom);
				out = aw.output(dataSource, out);
			// If the item is a container binding then output the container binding
			} else if (cw instanceof ContainerBinding){
				ContainerBinding<O> cb = (ContainerBinding<O>)cw;
				out = cb.output(out);
			}
		}
		return out;
	}
	
	/**
	 * Output this assembled wiget using the given object as source date for unset parameters
	 * @param dataSource		The source data for this assembled wiget
	 * @param out			The instance of the output type in which this assembled wiget is to be output
	 * @return				The instance of the output type after this assembled wiget has been output
	 */
	public O output(Object dataSource, O out) {

		// Check whether this wiget should be included or not. If it shouldn't be included then return the output instance unmodified 
		if (parent != null && includeCriteria != null) {
			if ( ! includeCriteria.evaluate(parent))
				return out;
		}
		
		// If the data source is a Collection
		if (dataSource instanceof Collection) {
			// For each item in the collection output this wiget assembly
			for (Object source : (Collection)dataSource) {
				out = outputNoCollection(source, out);
			}
		// If the data source is not a collection output this wiget assembly
		} else {
			out = outputNoCollection(dataSource, out);
		}
		return out;
	}
	
	/**
	 * Output this wiget assembly using the given datasource that is not a collection
	 * @param dataSource		The instance providing the source data for the parmeters of this wiget
	 * @param out			The instance of the output type into which this wiget assembly is to be output
	 * @return				The instance of the output type after this wiget assembly has been output
	 */
	@SuppressWarnings("unchecked")
	private O outputNoCollection(Object dataSource, O out) {
		
		// If the data source is the type required by the wiget output the wiget using the data source
		if (wiget.requiresType().isAssignableFrom(dataSource.getClass())) {
			return typedOutput((T)dataSource, out);
		}
		
		// If the data source is not of the required type attempt to adapt it to the required type using the adapter factory
		// registered with the assemblies wiget factory
		if (assembly.factory().getAdapterFactory() != null)
			if (assembly.factory().getAdapterFactory().hasAdapter(dataSource.getClass(), wiget.requiresType()))
				return typedOutput((T) assembly.factory().getAdapterFactory().adapt(dataSource, wiget.requiresType()), out);

		// If an instance of the wigets required type cannot be identified from the data source then output the wiget will null default
		// values for the wigets parameters
		return typedOutput(null, out);
		
	}
	
	/**
	 * Set this wiget assemblies data source to the given typed value and output the wiget using this assembled wiget.
	 * @param typedDataSource	The data source of the wigets required type
	 * @param out				The instance of the output type in which the wiget is to be output
	 * @return					The instance of the output type after the wiget has been output
	 */
	@SuppressWarnings("unchecked")
	private O typedOutput(T typedDataSource, O out) {
		this.typedDataSource = typedDataSource;
		return (O) wiget.output(this, out);
	}

	/**
	 * Get the value of the given parameter for this assembled wiget
	 * @param parameter		The wiget parameter whose value is required
	 * @return				The value of the given wiget parameter currently held by this assembled wiget
	 * @param <V>	The data type of the parameter requested
	 */
	@SuppressWarnings("unchecked")
	public <V> V get(WigetParameter<?, V> parameter) {
		// Evaluate the wiget parameter referenced by the this parameter on this assembled wigets model using this assembled wiget as an evaluator
		try {
			WigetParameter<W, V> wp = (WigetParameter<W, V>) parameter.getModelField().get(model);
			return wp.evaluate(this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new WigetError("Unable to get value of paramter {} from Wiget model {} - {}", e, parameter.getAlias(), model.getClass().getName(), e.getMessage());
		}
	}
	
	/**
	 * Get this contained wigets from the given container for this assembled wiget
	 * @param container	The container whose contents are required
	 * @return			The contents of the requested container
	 */
	private List<ContainedWiget> getContents(WigetContainer<W> container) {
		return get(container).getContents();
	}
	
	/**
	 * Get the container of this assembled wiget identified by the given wiget container
	 * @param container	The wiget container to get from this assembled wiget
	 * @return			This assembled wigets instance of the requested wiget container
	 */
	@SuppressWarnings("unchecked")
	private WigetContainer<W> get(WigetContainer<W> container) {
		// Get the wiget container from this assembled wigets model for the requested wiget container
		try {
			return (WigetContainer<W>) container.getModelField().get(model);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new WigetError("Unable to get model container for container {} in Wiget model {} - {}", e, container.getAlias(), model.getClass().getName(), e.getMessage());
		}
	}
	
	/**
	 * Set the value of the given parameter to the given value
	 * @param parameter	The parameter to set
	 * @param value		The value to set for the given parameter
	 * @return			This assembled wiget for method chaining
	 * @param <V> 	The data type of the parameter being set
	 */
	@SuppressWarnings("unchecked")
	public <V> AssembledWiget<F,O,W,T> set(WigetParameter<? extends W, V> parameter, V value) {
		
		// If the current value of this assembled wigets parameter for the parameter being set is already a ValuedWigetParameter
		// set the value of the valued wiget parameter. If it not a ValuedWigetParameter create a valued wiget parameter for
		// The given wiget parameter and value and set this assembled wiget model to the created valued wiget parameter for the
		// given parameter
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

	/**
	 * Bind the parameter of the source assembly to the the given parameter
	 * @param targetParameter	The parameter to bind
	 * @param sourceAssembly		The source assembly to which the target parameter will be bound
	 * @param sourceParameter	The parameter of the source assembly supplying the bound value
	 * @return	This assembled wiget for method chaining
	 * @param <V> 	The data type of the parameter being bound
	 */
	@SuppressWarnings("unchecked")
	public <V> AssembledWiget<F, O, ? extends Wiget, ?> bind( WigetParameter<? extends Wiget, V> targetParameter, AssembledWiget sourceAssembly, WigetParameter<? extends Wiget, V> sourceParameter) {
		try {
			// Create a bound wiget parameter for the given source parameter and source assembly and set the target parameter on this assembled wiget 
			// to the created bound parameter
			WigetParameter wp = (WigetParameter) sourceParameter.getModelField().get(sourceAssembly.model);
			
			BoundWigetParameter bwp = new BoundWigetParameter(targetParameter.getAlias(), targetParameter.getModelField(), wp, sourceAssembly);
			targetParameter.getModelField().set(model, bwp);
			
			return this;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new WigetError("Unable to bind the paramter {} from Wiget model {} to values from the parameter {} - {}", e, 
					targetParameter.getAlias(), 
					model.getClass().getName(), 
					sourceParameter.getAlias(), 
					e.getMessage());
		}		
	}
 
	/**
	 * It is not possible to change the parameters of a wiget
	 */
	@Override
	public void add(K2ParameterExpression<?> expr) {
		// Nothing to do, wiget parameters cannot be changed.
		if ( ! (expr instanceof WigetParameter))
			throw new WigetError("Unable to add non wiget parameters to a wiget assembly");
	}

	/**
	 * Wiget parameters are always considered set
	 */
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

	/**
	 * Add a wiget to the given container of this assembled wiget
	 * @param container				The container into which the wiget is to be assembled and added
	 * @param containedWigetType		The type of the wiget to add to the container
	 * @param sourceParameter		The parameter of this assembled wiget that will provide the data source for the added assembled wiget
	 * @return	The added assembled wiget created for the given wiget and source parameter
	 * @param <Q> 	The type of the wiget being added
	 */
	@SuppressWarnings("unchecked")
	public <Q extends Wiget> AssembledWiget<F,O,Q,?> add(WigetContainer<?> container, Class<Q> containedWigetType, WigetParameter<?, ?> sourceParameter) {
		AssembledWiget aw = new AssembledWiget(assembly, this, containedWigetType, sourceParameter);
		return get((WigetContainer<W>)container).add(aw);
	}
	
	/**
	 * Add a wiget to the given container of this assembled wiget that is conditional on the given predicate
	 * @param includeCriteria		The predicate controlling whether this wiget should be included in the output for this assembly
	 * @param container				The container into which the wiget is to be assembled and added
	 * @param containedWigetType		The type of the wiget to add to the container
	 * @param sourceParameter		The parameter of this assembled wiget that will provide the data source for the added assembled wiget
	 * @return		The added assembled wiget created for the given wiget and source parameter
	 * @param <Q> 	The type of the wiget being added
	 */
	@SuppressWarnings("unchecked")
	public <Q extends Wiget> AssembledWiget<F,O,Q,?> addIf(K2Predicate includeCriteria, WigetContainer<?> container, Class<Q> containedWigetType, WigetParameter<?, ?> sourceParameter) {
		AssembledWiget aw = new AssembledWiget(assembly, this, containedWigetType, sourceParameter);
		aw.setIncludeCriteria(includeCriteria);
		return get((WigetContainer<W>)container).add(aw);
	}
	
	/**
	 * Bind the contents of the from container in the source assembly to the given container. All the contents from the container of the source assembly will be included in the 
	 * output of the bound to container in the order they were added to the from container and sourrounded by contents added to the to container
	 * @param toContainer		The container to bind the from container contents to
	 * @param sourceAssembly		The assembled wiget that provides the bound contents for the from container
	 * @param fromContainer		The wiget container on the source assemble whose contents will be bound to the to container
	 * @return	This assembled wiget for method chaining
	 */
	@SuppressWarnings("unchecked")
	public AssembledWiget<F, O, ? extends Wiget, ?> bind(WigetContainer<?> toContainer, AssembledWiget<F, O, ? extends Wiget, ?> sourceAssembly, WigetContainer<?> fromContainer) {
		ContainerBinding cb = new ContainerBinding(sourceAssembly, fromContainer);
		get((WigetContainer<W>)toContainer).add(cb);
		return this;
		
	}

	/**
	 * Go back up one level on the assembled wiget hierarchy
	 * @return	The parent wiget assembly
	 */
	public AssembledWiget<F,O,?,?> up() { return parent; }
	
	/**
	 * Go beck the to root assembled wiget
	 * @return	The root assembled wiget
	 */
	public AssembledWiget<F,O,?,?> root() { return assembly.root(); }


}
