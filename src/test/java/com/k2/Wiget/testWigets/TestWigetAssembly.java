package com.k2.Wiget.testWigets;

import java.io.PrintWriter;

import com.k2.Wiget.Wiget;
import com.k2.Wiget.WigetAssembly;
import com.k2.Wiget.WigetFactory;
import com.k2.Wiget.WigetParameter;
import com.k2.Wiget.testTypes.TypeA;
import com.k2.Wiget.testWigets.spec.TestWigetA;

@SuppressWarnings("rawtypes")
public class TestWigetAssembly<W extends Wiget,T> extends WigetAssembly<TestWigetFamily,PrintWriter, W,T> {

	public TestWigetAssembly(
			WigetFactory<TestWigetFamily, PrintWriter> factory, 
			Class<W> wigetType) {
		super(factory, wigetType);
	}
	
	
	

}
