package com.k2.Wiget;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wiget container identifies a container of a wiget.  Wiget containers can contain other wigets to be included in a wiget when the 
 * wiget is output.
 * @author simon
 *
 * @param <W>	The wiget type for whcih this is a container
 */
@SuppressWarnings("rawtypes")
public class WigetContainer<W extends Wiget> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final String alias;
	/**
	 * @return	The alias uniquely idetifying this container within the wiget
	 */
	public String getAlias() { return alias; }
	
	private final Field modelField;
	/**
	 * @return	The field of the static model that represents this container.
	 */
	public Field getModelField() { return modelField; }
	
	private List<ContainedWiget> contents = null;
	/**
	 * @return	Get the contained wigets (assembled wigets or container bindings) that are contained in this instance of this wiget container
	 */
	List<ContainedWiget> getContents() { return (contents!=null) ? contents : new ArrayList<ContainedWiget>(); }
	/**
	 * Add the given assembled wiget to this container
	 * @param aw		The assembled wiget to be added to the container
	 * @return		The assembled wiget that was added to the container for method chaining
	 */
	AssembledWiget add(AssembledWiget aw) {
		if (contents==null)
			contents = new ArrayList<ContainedWiget>();
		contents.add(aw);
		return aw;
	}
	/**
	 * Add the given container binding to this container
	 * @param cb		The container binding that binds the contents of another container to be contents of this container
	 */
	void add(ContainerBinding cb) {
		if (contents==null)
			contents = new ArrayList<ContainedWiget>();
		contents.add(cb);
	}
	
	/**
	 * Create a new instance of the wiget container for the given container alias and field of the wiget static model.
	 * @param alias			The alias of this container
	 * @param modelField		The field of the static model representing this container.
	 */
	WigetContainer(String alias, Field modelField) {
		this.alias = alias;
		this.modelField = modelField;
		logger.trace("Created Wiget container {} for field {}:{}", alias, modelField.getDeclaringClass().getName(), modelField.getName());
	}
	

}
