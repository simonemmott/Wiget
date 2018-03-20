package com.k2.Wiget;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.k2.Adapter.AdapterFactory;
import com.k2.Util.ObjectUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.Wiget.annotation.WigetImplementation;

/**
 * A wiget factory supplied implementations of wigets requested by their implementing interfaces or wiget alias.
 * 
 * Each factory supplies wigets for a single family of wigets and all wigets in a given family output on the same type of object.
 * 
 * Consequetly  in the absence of any other limitations any wiget within a wiget family can contain any other wiget with in the same family 
 * all of which are supplied by the same wiget factory.
 * 
 * Each wiget family defines a list of the required wigets for the family. If the factory is not created with implementations of each of the 
 * wigets required by the family the an unchecked WigetError is thrown by the wiget factory &lt;init&gt; method
 * 
 * @author simon
 *
 * @param <F>	The wiget family
 * @param <O>	The output type of all wiget provided by this factory
 */
public class WigetFactory<F extends WigetFamily<O>, O> {
	
	private final F family;
	/**
	 * @return	The wiget family
	 */
	public final F family() { return family; }
	
	private final Class<O> outputType;
	/**
	 * @return	The output type of all wigets supplied by the wiget factory
	 */
	public final Class<O> outputType() { return outputType; }
	
	AdapterFactory adapters;
	/**
	 * @return	The adapter factory that is automatically used to adaprt data source objects into an instance of the wigets requires type if necessary and possible
	 */
	public AdapterFactory getAdapterFactory() { 
		return adapters; 
	}
	/**
	 * Set the adapter factory that is automatically used to adaprt data source objects into an instance of the wigets requires type if necessary and possible
	 * @param adapters	The adapter factory used by wigets supplied by this wiget factory
	 */
	public void setAdapterFactory(AdapterFactory adapters) { this.adapters = adapters; }
	
	private Map<Class<? extends Wiget<F,O,?>>, Wiget<F,O,?>> implementationsByType = new HashMap<Class<? extends Wiget<F,O,?>>, Wiget<F,O,?>>();
	private Map<String, Wiget<F,O,?>> implementationsByAlias = new HashMap<String, Wiget<F,O,?>>();
	
	/**
	 * Create an instance of the wiget factory for the given family, output type and array of package names
	 * 
	 * Each package in the array of package names and all the sub packages will be scanned for classes annotated with the WigetImplementation annotation.
	 * An instance of each such class is generated and registered with the wiget factory indexed by the implementing interface and also the wiget alias.
	 * 
	 * @param family			The wiget family
	 * @param outputType		The wiget output type
	 * @param packageNames	The array of package names to scan for wiget implementations
	 */
	@SuppressWarnings("unchecked")
	public WigetFactory(F family, Class<O> outputType, String ... packageNames) {
		this.family = family;
		this.outputType = outputType;
		for (String packageName : packageNames) {
			registerImplementationClasses((Class<? extends Wiget<F,O,?>>[]) ClassUtil.getClasses(packageName, WigetImplementation.class));
		}
		checkdWigetsImplemented();
	}
	
	/**
	 * Create an instance of the wiget factory for the given family, output type and array of wiget implementations
	 * @param family			The wiget family
	 * @param outputType		The wiget output type
	 * @param wigetImplementationClasses		The array of wiget implementations
	 */
	@SafeVarargs
	public WigetFactory(F family, Class<O> outputType, Class<? extends Wiget<F,O,?>> ... wigetImplementationClasses) {
		this.family = family;
		this.outputType = outputType;
		registerImplementationClasses(wigetImplementationClasses);
		checkdWigetsImplemented();
	}
	
	@SuppressWarnings("unchecked")
	private void registerImplementationClasses(Class<? extends Wiget<F,O,?>> ... wigetImplementationClasses) {
		for (Class<? extends Wiget<F,O,?>> wigetImplementationClass : wigetImplementationClasses ) {
			Class<? extends Wiget<F,O,?>> wigetType = null;
			for (Class<?> iFace : wigetImplementationClass.getInterfaces()) {
				if (Wiget.class.isAssignableFrom(iFace)) {
					wigetType = (Class<? extends Wiget<F,O,?>>) iFace;
					break;
				}
			}
			if (wigetType == null)
				throw new WigetError("The class {} does not implement the Wiget interface", wigetImplementationClass.getName());
			try {
				Wiget<F,O,?> wiget = wigetImplementationClass.newInstance();

				Object staticModel = wiget.staticModel();
				Object generatedModel = wiget.generateModel();
				ObjectUtil.copy(generatedModel, staticModel);

				implementationsByType.put(wigetType, wiget);
				implementationsByAlias.put(wiget.alias(), wiget);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new WigetError("Unable to create wiget for class {}. No available zero arg constructor.", e, wigetImplementationClass.getName());
			}
		}
	}
	
	private void checkdWigetsImplemented() {
		Collection<Wiget<F,O,?>> imps = implementationsByType.values();
		for (Class<? extends Wiget<? extends WigetFamily<O>,O,?>> requiredClass : family.requiredWigets()) {
			boolean assignable = false;
			for (Wiget<F,O,?> imp : imps) {
				if (requiredClass.isAssignableFrom(imp.getClass())) {
					assignable = true;
					break;
				}
			}
			if ( ! assignable)
				throw new WigetError("The required wiget {} is not implemented in this factory", requiredClass.getName());
		}
	}

	/**
	 * Get the implementation of the given wiget interface
	 * If the factory does not include an implementation of the requested wiget interface then an unchecked WigetError is thrown
	 * @param wigetType	The required wiget interface
	 * @return		The implementation of the wiget interface registered with this factory
	 * @param <W> The type of the required wiget
	 */
	public <W extends Wiget<F,O,?>> Wiget<F,O,?> getWiget(Class<W> wigetType) { 
		Wiget<F,O,?> w = implementationsByType.get(wigetType);
		if (w!=null)
			return w;
		throw new WigetError("No wiget defined for the type {}", wigetType.getName());
	}
	/**
	 * Get the implementation of the given wiget alias
	 * If the factory does not include an implementation of the requested wiget alias then an unchecked WigetError is thrown
	 * @param alias		The required wiget alias
	 * @return			The implementation of the wiget alias registered with this factory
	 */
	public Wiget<F,O,?> getWiget(String alias) { 
		Wiget<F,O,?> w = implementationsByAlias.get(alias);
		if (w!=null)
			return w;
		throw new WigetError("No wiget defined for the alias {}", alias);
	}
	
	/**
	 * Create and return a generic wiget assembly with the requested wiget as the root assembled wiget
	 * @param wigetType	The wiget to use as the root assembled wiget
	 * @return	The generic wiget assembly
	 * @param <W> The type of the required wiget
	 * @param <T> The requires data type of the wiget
	 */
	@SuppressWarnings("rawtypes")
	public <W extends Wiget,T> WigetAssembly<F,O,W,T> getAssembly(Class<W> wigetType) {
		return new WigetAssembly<F,O,W,T>(this, wigetType);
	}
	
	/**
	 * Create and return a specific extension of the generic wiget assembly. Typically a wiget family will be supplied with a
	 * specific wiget assembly. This method allows the generic wiget factory to create and return the specific wiget assembly
	 * 
	 * @param assemblyClass	The class that extends the WigetAssembly for the given wiget family
	 * @param wigetType	The wiget type to be used as the root wiget of the resultant assembly
	 * @return		A wiget assembly of the required assembly class and with the required root assembled wiget
	 * @param <W> The type of the required wiget
	 * @param <T> The requires data type of the wiget
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <W extends Wiget,T> WigetAssembly<F,O,W,T> getAssembly(Class<? extends WigetAssembly> assemblyClass, Class<W> wigetType) {
			try {
				Constructor ac = assemblyClass.getConstructor(WigetFactory.class, Class.class);
				return (WigetAssembly<F, O, W, T>) ac.newInstance(this, wigetType);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new WigetError("Unable to create new WigetAssembly as instance of {} - {}", e, assemblyClass.getName(), e.getMessage());
			}
	}
	

	
}
