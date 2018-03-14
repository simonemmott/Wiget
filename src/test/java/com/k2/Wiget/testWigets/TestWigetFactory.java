package com.k2.Wiget.testWigets;

import java.io.PrintWriter;

import com.k2.Wiget.Wiget;
import com.k2.Wiget.WigetFactory;

public class TestWigetFactory extends WigetFactory<TestWigetFamily, PrintWriter> {

	public TestWigetFactory(String ... packageNames) {
		super(new TestWigetFamily(), PrintWriter.class, packageNames);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <W extends Wiget,T> TestWigetAssembly<W,T> getAssembly(Class<W> wigetType) {
		return (TestWigetAssembly<W,T>) super.getAssembly(TestWigetAssembly.class, wigetType);
	}

}
