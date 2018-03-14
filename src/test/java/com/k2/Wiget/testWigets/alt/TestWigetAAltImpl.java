package com.k2.Wiget.testWigets.alt;

import java.io.PrintWriter;

import com.k2.Wiget.annotation.WigetImplementation;
import com.k2.Wiget.AssembledWiget;
import com.k2.Wiget.Wiget;
import com.k2.Wiget.testTypes.TypeA;
import com.k2.Wiget.testWigets.TestWigetFamily;
import com.k2.Wiget.testWigets.spec.ATestWiget;
import com.k2.Wiget.testWigets.spec.TestWigetA;

@WigetImplementation
public class TestWigetAAltImpl extends ATestWiget<TypeA> implements TestWigetA{
	
	@SuppressWarnings("rawtypes")
	@Override
	public PrintWriter output(
			AssembledWiget<TestWigetFamily, PrintWriter, ? extends Wiget, TypeA> a,
			PrintWriter out) {
		
		out.println("Alternative Test wiget A");
		out.println("Number: "+a.get(TestWigetA.model.number));
		out.println("Alias: "+a.get(TestWigetA.model.alias));
		out.println("Title: "+a.get(TestWigetA.model.title));
		
		return out;
	}




	
	

}
