package com.k2.Wiget.testWigets.spec;

import com.k2.Wiget.annotation.WigetSpecification;
import com.k2.Wiget.WigetContainer;
import com.k2.Wiget.WigetParameter;
import com.k2.Wiget.testTypes.TypeB;

@WigetSpecification()
public interface TestWigetB extends TestWiget<TypeB> {
	
	public static class Model {
		public WigetParameter<TestWigetB, Long> longNumber;
		public WigetParameter<TestWigetB, String> name;
		public WigetParameter<TestWigetB, String> description;
		public WigetContainer<TestWigetB> cont1;
		public WigetContainer<TestWigetB> cont2;
	}
	public static Model model = new Model();
	@Override public default Object staticModel() { return model; }
	@Override public default Class<?> modelType() { return Model.class; }
	
}
