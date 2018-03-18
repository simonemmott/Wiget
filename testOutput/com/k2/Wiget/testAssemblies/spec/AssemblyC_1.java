package com.k2.Wiget.testAssemblies.spec;

import com.k2.Wiget.WigetContainer;
import com.k2.Wiget.WigetParameter;
import com.k2.Wiget.annotation.WigetSpecification;
import com.k2.Wiget.testTypes.TypeA;
import com.k2.Wiget.testTypes.TypeB;
import com.k2.Wiget.testTypes.TypeC;
import com.k2.Wiget.testWigets.spec.TestWiget;
import java.lang.String;
import java.util.List;

@WigetSpecification()
public interface AssemblyC_1 extends TestWiget<TypeC> {

	public static class Model {
		public WigetParameter<AssemblyC_1, String> alias;
		public WigetParameter<AssemblyC_1, String> name;
		public WigetParameter<AssemblyC_1, String> description;
		public WigetParameter<AssemblyC_1, TypeA> a;
		public WigetParameter<AssemblyC_1, List<TypeB>> bs;
		public WigetContainer<AssemblyC_1> cont1;
	}

	public static final Model model = new Model();
	@Override public default Object staticModel() { return model; }
	@Override public default Class<?> modelType() { return Model.class; }

}
