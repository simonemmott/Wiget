package com.k2.Wiget;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class WigetContainer<W extends Wiget> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final String alias;
	public String getAlias() { return alias; }
	
	private final Field modelField;
	public Field getModelField() { return modelField; }
	
	private List<AssembledWiget> contents = null;
	List<AssembledWiget> getContents() { return (contents!=null) ? contents : new ArrayList<AssembledWiget>(); }
	AssembledWiget add(AssembledWiget aw) {
		if (contents==null)
			contents = new ArrayList<AssembledWiget>();
		contents.add(aw);
		return aw;
	}
	
	WigetContainer(String alias, Field modelField) {
		this.alias = alias;
		this.modelField = modelField;
		logger.trace("Created Wiget container {} for field {}:{}", alias, modelField.getDeclaringClass().getName(), modelField.getName());
	}
	

}
