package com.k2.Wiget.testWigets.impl;

import java.io.PrintWriter;

import com.k2.Wiget.annotation.WigetImplementation;
import com.k2.Wiget.AssembledWiget;
import com.k2.Wiget.Wiget;
import com.k2.Wiget.testTypes.TypeB;
import com.k2.Wiget.testWigets.TestWigetFamily;
import com.k2.Wiget.testWigets.spec.ATestWiget;
import com.k2.Wiget.testWigets.spec.TestWigetB;
import com.k2.Wiget.testWigets.spec.TestWigetC;

@WigetImplementation
public class TestWigetBImpl extends ATestWiget<TypeB> implements TestWigetB{
	
	@SuppressWarnings("rawtypes")
	@Override
	public PrintWriter output(
			AssembledWiget<TestWigetFamily, PrintWriter, ? extends Wiget, TypeB> a,
			PrintWriter out) {
		
		out.println("Test wiget B");
		out.println("Long Number: "+a.get(TestWigetB.model.longNumber));
		out.println("Name: "+a.get(TestWigetB.model.name));
		out.println("Title: "+a.get(TestWigetB.model.description));
		out.println("");
		out.println("Container B 1 {");
		out = a.outputContents(TestWigetB.model.cont1, out);
		out.println("}");
		out.println("Container B 2 {");
		out = a.outputContents(TestWigetB.model.cont2, out);
		out.println("}");
		
		return out;
	}



	
	

}
