package com.k2.Wiget.testWigets.spec;

import com.k2.Wiget.annotation.WigetSpecification;
import com.k2.Wiget.WigetContainer;
import com.k2.Wiget.WigetParameter;
import com.k2.Wiget.testTypes.TypeA;

@WigetSpecification
public interface TestWigetA extends TestWiget<TypeA> {
	
	public static class Model{
		public WigetParameter<TestWigetA, Integer> number;
		public WigetParameter<TestWigetA, String> alias;
		public WigetParameter<TestWigetA, String> title;
		public WigetContainer<TestWigetA> cont1;
		public WigetContainer<TestWigetA> cont2;
	}
	public static final Model model = new Model();
	@Override public default Object staticModel() { return model; }
	@Override public default Class<?> modelType() { return Model.class; }
	
}
