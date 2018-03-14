package com.k2.Wiget.testWigets.spec;

import java.util.List;

import com.k2.Wiget.annotation.WigetSpecification;
import com.k2.Wiget.WigetContainer;
import com.k2.Wiget.WigetParameter;
import com.k2.Wiget.testTypes.TypeA;
import com.k2.Wiget.testTypes.TypeB;
import com.k2.Wiget.testTypes.TypeC;
import com.k2.Wiget.testWigets.spec.TestWigetB.Model;

@WigetSpecification()
public interface TestWigetC extends TestWiget<TypeC> {
	
	public static class Model {
		public WigetParameter<TestWigetC, String> alias;
		public WigetParameter<TestWigetC, String> name;
		public WigetParameter<TestWigetC, String> description;
		public WigetParameter<TestWigetC, TypeA> a;
		public WigetParameter<TestWigetC, List<TypeB>> bs;
		public WigetContainer<TestWigetC> cont1;
		public WigetContainer<TestWigetC> cont2;
	}

	public static final Model model = new Model();
	@Override public default Object staticModel() { return model; }
	@Override public default Class<?> modelType() { return Model.class; }
	
}
