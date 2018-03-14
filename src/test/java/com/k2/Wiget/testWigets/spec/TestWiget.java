package com.k2.Wiget.testWigets.spec;

import java.io.PrintWriter;

import com.k2.Wiget.Wiget;
import com.k2.Wiget.testWigets.TestWigetFamily;
import com.k2.Wiget.testWigets.spec.TestWigetB.Model;

public interface TestWiget<T> extends Wiget<TestWigetFamily, PrintWriter, T> {

	public default Class<?> modelType() { return null; }

}
