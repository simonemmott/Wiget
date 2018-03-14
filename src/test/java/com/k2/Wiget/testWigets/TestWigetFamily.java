package com.k2.Wiget.testWigets;

import java.io.PrintWriter;

import com.k2.Wiget.WigetFamily;
import com.k2.Wiget.testWigets.spec.*;

public class TestWigetFamily extends WigetFamily<PrintWriter> {

	public TestWigetFamily() {
		super("TestWigetFamily", PrintWriter.class, TestWigetA.class, TestWigetB.class, TestWigetC.class);
	}

}
