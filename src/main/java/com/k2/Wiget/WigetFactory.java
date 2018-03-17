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

public class WigetFactory<F extends WigetFamily<O>, O> {
	
	private final F family;
	public final F family() { return family; }
	
	private final Class<O> outputType;
	public final Class<O> outputType() { return outputType; }
	
	AdapterFactory adapters;
	public AdapterFactory getAdapterFactory() { 
		return adapters; 
	}
	public void setAdapterFactory(AdapterFactory adapters) { this.adapters = adapters; }
	
	private Map<Class<? extends Wiget<F,O,?>>, Wiget<F,O,?>> implementationsByType = new HashMap<Class<? extends Wiget<F,O,?>>, Wiget<F,O,?>>();
	private Map<String, Wiget<F,O,?>> implementationsByAlias = new HashMap<String, Wiget<F,O,?>>();
	
	@SuppressWarnings("unchecked")
	public WigetFactory(F family, Class<O> outputType, String ... packageNames) {
		this.family = family;
		this.outputType = outputType;
		for (String packageName : packageNames) {
			registerImplementationClasses((Class<? extends Wiget<F,O,?>>[]) ClassUtil.getClasses(packageName, WigetImplementation.class));
		}
		checkdWigetsImplemented();
	}
	
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

	public <W extends Wiget<F,O,?>> Wiget<F,O,?> getWiget(Class<W> wigetType) { 
		Wiget<F,O,?> w = implementationsByType.get(wigetType);
		if (w!=null)
			return w;
		throw new WigetError("No wiget defined for the type {}", wigetType.getName());
	}
	
	public Wiget<F,O,?> getWiget(String alias) { 
		Wiget<F,O,?> w = implementationsByAlias.get(alias);
		if (w!=null)
			return w;
		throw new WigetError("No wiget defined for the alias {}", alias);
	}
	
	
	@SuppressWarnings("rawtypes")
	public <W extends Wiget,T> WigetAssembly<F,O,W,T> getAssembly(Class<W> wigetType) {
		return new WigetAssembly<F,O,W,T>(this, wigetType);
	}
	
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
