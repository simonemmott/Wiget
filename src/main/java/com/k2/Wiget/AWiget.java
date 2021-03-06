package com.k2.Wiget;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Util.StringUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.Wiget.annotation.WigetImplementation;

/**
 * The abstract wiget AWiget provides the basic structure for all wiget implementations.
 * 
 * The abstract wiget unpacks the implementing wiget class to extract the wiget alias and requires type for the wiget from the definition of the implementing class.
 * @author simon
 *
 * @param <F>	The wiget family
 * @param <O>	The wigets output type
 * @param <T>	The wiget requires data type
 */
public abstract class AWiget<F extends WigetFamily<O>,O,T> implements Wiget<F, O, T> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final String alias;
	@Override public String alias() { return alias; }

	private final Class<T> requiresType;
	@Override public Class<T> requiresType() { return requiresType; }

	/**
	 * Construct a wiget and extract the alias and requires type from the implementing class definition.
	 * If the wiget is incorrectly implemented this method will fail with the unchecked WigetError
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AWiget() {
		if ( ! this.getClass().isAnnotationPresent(WigetImplementation.class))
			throw new WigetError("The provided wiget {} is not annotated with @WigetImplementation", this.getClass().getName());
		WigetImplementation wi = this.getClass().getAnnotation(WigetImplementation.class);
		this.requiresType = ClassUtil.getClassGenericTypeClass(this.getClass(), 0);
		Class<? extends Wiget> iFace = null;
		for (Class<?> iF : this.getClass().getInterfaces()) {
			if (Wiget.class.isAssignableFrom(iF)) {
				iFace = (Class<? extends Wiget>) iF;
				break;
			}
		}

		if (iFace == null)
			throw new WigetError("The provided wiget {} does not implement the Wiget interface", this.getClass().getName());
		this.alias = (StringUtil.isSet(wi.name())) ? wi.name() : iFace.getSimpleName();
		
		logger.trace("Building wiget {} from implementation {}", this.alias, this.getClass().getName());
		logger.trace("Wiget {} implements {} and requires {}", this.alias, iFace.getName(), this.requiresType.getName());
		
		if (this.requiresType == null)
			throw new WigetError("The implemention the Wiget interface of the provided wiget {} does not define the generic required type", this.getClass().getName());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object generateModel() {
		logger.trace("Generating wiget model for {} requires {}", this.getClass().getName(), requiresType.getSimpleName());
		try {
			Object model = modelType().newInstance();
			for (Method m : ClassUtil.getAllMethods(requiresType)) {
				logger.trace("Checking wiget parameter for getter {}.{}()",requiresType.getSimpleName(), m.getName());
				if (m.getParameterCount()==0 && m.getReturnType() != void.class  && m.getName().startsWith("get")) {
					String alias = ClassUtil.getAliasFromMethod(m);
					logger.trace("Assiigning wiget paramter for getter method {}.{}() to static model field with alias {}", requiresType.getSimpleName(), m.getName(), alias) ; 
					Field f = ClassUtil.getField(model.getClass(), alias, false);
					if (f != null)
						f.set(model, new MappedWigetParameter(alias, f, m));
					else
						logger.warn("No static model field available for {} wiget {}", alias, this.getClass().getName());
				}
			}
			for (Field f : ClassUtil.getDeclaredFields(model.getClass())) {
				if (WigetContainer.class.isAssignableFrom(f.getType())) {
					f.set(model, new WigetContainer(f.getName(), f));
				}
			}
			return model;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new WigetError("Unable to create a new instance of the model of the provided wiget {} - {}",e , this.getClass().getName(), e.getMessage());
		}
	}


}
