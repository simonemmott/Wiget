package com.k2.Wiget.testAssemblies.impl;

import java.io.PrintWriter;

import com.k2.Wiget.annotation.WigetImplementation;
import com.k2.Wiget.testAssemblies.spec.AssemblyC_1;
import com.k2.Wiget.AssembledWiget;
import com.k2.Wiget.Wiget;
import com.k2.Wiget.testTypes.TypeA;
import com.k2.Wiget.testTypes.TypeC;
import com.k2.Wiget.testWigets.TestWigetAssembly;
import com.k2.Wiget.testWigets.TestWigetFactory;
import com.k2.Wiget.testWigets.TestWigetFamily;
import com.k2.Wiget.testWigets.spec.ATestWiget;
import com.k2.Wiget.testWigets.spec.TestWigetA;
import com.k2.Wiget.testWigets.spec.TestWigetB;
import com.k2.Wiget.testWigets.spec.TestWigetC;

@WigetImplementation
public class AssemblyC_1Impl extends ATestWiget<TypeC> implements AssemblyC_1{
	
	@SuppressWarnings("rawtypes")
	@Override
	public PrintWriter output(
			AssembledWiget<TestWigetFamily, PrintWriter, ? extends Wiget, TypeC> a,
			PrintWriter out) {
		
		TestWigetFactory factory = (TestWigetFactory) a.assembly().factory();
		
		TestWigetAssembly<TestWigetC, TypeC> templateAssembly = factory.getAssembly(TestWigetC.class);
		templateAssembly.root()
			.set(TestWigetC.model.alias, "SetAlias")
			.bind(TestWigetC.model.name, a, AssemblyC_1.model.alias)
			.bind(TestWigetC.model.description, a, AssemblyC_1.model.name)
			.add(TestWigetC.model.cont1, TestWigetA.class, TestWigetC.model.a)
				.set(TestWigetA.model.alias, "SetAlias")
				.up()
			.add(TestWigetC.model.cont1, TestWigetA.class, TestWigetC.model.a)
				.set(TestWigetA.model.title, "Set Title")
				.root()
			.add(TestWigetC.model.cont2, TestWigetB.class, TestWigetC.model.bs)
				.bind(TestWigetB.model.description, a, AssemblyC_1.model.description)
				.bind(TestWigetB.model.cont2, a, AssemblyC_1.model.cont1);

		
		return templateAssembly.output(a.getDataSource(), out);
		
	}


	
	

}
