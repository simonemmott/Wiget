package com.k2.Wiget.testWigets.impl;

import java.io.PrintWriter;

import com.k2.Wiget.annotation.WigetImplementation;
import com.k2.Wiget.AssembledWiget;
import com.k2.Wiget.Wiget;
import com.k2.Wiget.testTypes.TypeA;
import com.k2.Wiget.testTypes.TypeC;
import com.k2.Wiget.testWigets.TestWigetAssembly;
import com.k2.Wiget.testWigets.TestWigetFactory;
import com.k2.Wiget.testWigets.TestWigetFamily;
import com.k2.Wiget.testWigets.spec.ATestWiget;
import com.k2.Wiget.testWigets.spec.TestWigetA;
import com.k2.Wiget.testWigets.spec.TestWigetC;

@WigetImplementation
public class TestWigetCImpl extends ATestWiget<TypeC> implements TestWigetC{
	
	@SuppressWarnings("rawtypes")
	@Override
	public PrintWriter output(
			AssembledWiget<TestWigetFamily, PrintWriter, ? extends Wiget, TypeC> a,
			PrintWriter out) {
		
		TestWigetFactory factory = (TestWigetFactory) a.assembly().factory();
		TestWigetAssembly<TestWigetA, TypeA> twaA = factory.getAssembly(TestWigetA.class);

		out.println("Test wiget C");
		out.println("Alias: "+a.get(TestWigetC.model.alias));
		out.println("Name: "+a.get(TestWigetC.model.name));
		out.println("Description: "+a.get(TestWigetC.model.description));
		out.println("");
		out = twaA.output(a.get(TestWigetC.model.a), out);
		out.println("");
		out.println("Container C 1 {");
		
		out = a.outputContents(TestWigetC.model.cont1, out);

		out.println("}");

		out.println("Container C 2 {");

		out = a.outputContents(TestWigetC.model.cont2, out);
		
		out.println("}");
		
		return out;
	}


	
	

}
