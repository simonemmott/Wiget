package com.k2.Wiget;

import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Adapter.AdapterFactory;
import com.k2.Expressions.predicate.PredicateBuilder;
import com.k2.Wiget.testTypes.*;
import com.k2.Wiget.testWigets.TestWigetAssembly;
import com.k2.Wiget.testWigets.TestWigetFactory;
import com.k2.Wiget.testWigets.spec.*;

public class WigetFactoryTest {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Test
	public void wigetATest() {

		TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl");
		
		TypeA a = new TypeA(10, "This", "Is wiget A");
		
		TestWigetAssembly<TestWigetA, TypeA> wa = factory.getAssembly(TestWigetA.class);	
		
		StringWriter sw = new StringWriter();
		
		wa.output(a, new PrintWriter(sw)).flush();
		
		String expected = 
				"Test wiget A\n" + 
				"Number: 10\n" + 
				"Alias: This\n" + 
				"Title: Is wiget A\n";
		System.out.println(sw.toString());
		assertEquals(expected, sw.toString());
		
	}

	@Test
	public void incompleteFammilyTest() {

		TestWigetFactory factory = null;
		String errMessage = "";
		try {
			factory = new TestWigetFactory("com.k2.Wiget.testWigets.incomplete");
		} catch (WigetError e) {
			errMessage = e.getMessage();
		}
		
		assertNull(factory);
		assertEquals("The required wiget com.k2.Wiget.testWigets.spec.TestWigetC is not implemented in this factory", errMessage);
				
	}

	@Test
	public void wigetBTest() {

		TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl");
		
		TypeB b = new TypeB(123L, "Hello", "World!");
		
		TestWigetAssembly<TestWigetB, TypeB> wa = factory.getAssembly(TestWigetB.class);	
		
		StringWriter sw = new StringWriter();
		
		wa.output(b, new PrintWriter(sw)).flush();
		
		String expected = 
				"Test wiget B\n" + 
				"Long Number: 123\n" + 
				"Name: Hello\n" + 
				"Title: World!\n" + 
				"\n" + 
				"Container B 1 {\n" + 
				"}\n" + 
				"Container B 2 {\n" + 
				"}\n";
		System.out.println(sw.toString());
		assertEquals(expected, sw.toString());
		
	}

	@Test
	public void adaptedTest() {

		TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl");
		
		AdapterFactory adapters = AdapterFactory.register("com.k2.Wiget.testAdapters");
		
		factory.setAdapterFactory(adapters);
		
		TypeA a = new TypeA(10, "This", "Has been adapted");
		
		TestWigetAssembly<TestWigetB, TypeB> wa = factory.getAssembly(TestWigetB.class);	
		
		StringWriter sw = new StringWriter();
		
		wa.output(a, new PrintWriter(sw)).flush();
		
		String expected = 
				"Test wiget B\n" + 
				"Long Number: 10\n" + 
				"Name: This\n" + 
				"Title: Has been adapted\n" + 
				"\n" + 
				"Container B 1 {\n" + 
				"}\n" + 
				"Container B 2 {\n" + 
				"}\n";
		System.out.println(sw.toString());
		assertEquals(expected, sw.toString());
		
	}

	@Test
	public void alternativeWigetTest() {

		TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.alt");
		
		TypeA a = new TypeA(10, "This", "Is wiget A");
		
		TestWigetAssembly<TestWigetA, TypeA> wa = factory.getAssembly(TestWigetA.class);	
		
		StringWriter sw = new StringWriter();
		
		wa.output(a, new PrintWriter(sw)).flush();
		
		String expected = 
				"Alternative Test wiget A\n" + 
				"Number: 10\n" + 
				"Alias: This\n" + 
				"Title: Is wiget A\n";
		System.out.println(sw.toString());
		assertEquals(expected, sw.toString());
		
	}

	@Test
	public void collectionWigetTest() {

		TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl");
		
		
		List<TypeA> aList = new ArrayList<TypeA>();
		aList.add(new TypeA(1, "This", "Is wiget A 1"));
		aList.add(new TypeA(2, "This", "Is wiget A 2"));
		aList.add(new TypeA(3, "This", "Is wiget A 3"));
		
		TestWigetAssembly<TestWigetA, TypeA> wa = factory.getAssembly(TestWigetA.class);	
		
		StringWriter sw = new StringWriter();
		
		wa.output(aList, new PrintWriter(sw)).flush();
		
		String expected = 
				"Test wiget A\n" + 
				"Number: 1\n" + 
				"Alias: This\n" + 
				"Title: Is wiget A 1\n" +
				"Test wiget A\n" + 
				"Number: 2\n" + 
				"Alias: This\n" + 
				"Title: Is wiget A 2\n" +
				"Test wiget A\n" + 
				"Number: 3\n" + 
				"Alias: This\n" + 
				"Title: Is wiget A 3\n";
		System.out.println(sw.toString());
		assertEquals(expected, sw.toString());
		
	}

	@Test
	public void adaptedCollectionWigetTest() {

		TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl");
		
		AdapterFactory adapters = AdapterFactory.register("com.k2.Wiget.testAdapters");
		
		factory.setAdapterFactory(adapters);
				
		List<TypeA> aList = new ArrayList<TypeA>();
		aList.add(new TypeA(1, "This", "Is wiget A 1"));
		aList.add(new TypeA(2, "This", "Is wiget A 2"));
		aList.add(new TypeA(3, "This", "Is wiget A 3"));
		
		TestWigetAssembly<TestWigetB, TypeB> wa = factory.getAssembly(TestWigetB.class);	
		
		StringWriter sw = new StringWriter();
		
		wa.output(aList, new PrintWriter(sw)).flush();
		
		String expected = 
				"Test wiget B\n" + 
				"Long Number: 1\n" + 
				"Name: This\n" + 
				"Title: Is wiget A 1\n" + 
				"\n" + 
				"Container B 1 {\n" + 
				"}\n" + 
				"Container B 2 {\n" + 
				"}\n" +
				"Test wiget B\n" + 
				"Long Number: 2\n" + 
				"Name: This\n" + 
				"Title: Is wiget A 2\n" + 
				"\n" + 
				"Container B 1 {\n" + 
				"}\n" + 
				"Container B 2 {\n" + 
				"}\n" +
				"Test wiget B\n" + 
				"Long Number: 3\n" + 
				"Name: This\n" + 
				"Title: Is wiget A 3\n" + 
				"\n" + 
				"Container B 1 {\n" + 
				"}\n" + 
				"Container B 2 {\n" + 
				"}\n";
		System.out.println(sw.toString());
		assertEquals(expected, sw.toString());
		
	}

	@Test
	public void setValueTest() {

		TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl");
		
		TypeA a = new TypeA(10, "This", "Is wiget A");
		
		TestWigetAssembly<TestWigetA, TypeA> wa = factory.getAssembly(TestWigetA.class);
		wa.root()
				.set(TestWigetA.model.number, 1)
				.set(TestWigetA.model.alias, "SetAlias");
		
		StringWriter sw = new StringWriter();
		
		wa.output(a, new PrintWriter(sw)).flush();
		
		String expected = 
				"Test wiget A\n" + 
				"Number: 1\n" + 
				"Alias: SetAlias\n" + 
				"Title: Is wiget A\n";
		System.out.println(sw.toString());
		assertEquals(expected, sw.toString());
		
	}

	@Test
	public void setValueCollectionTest() {

		TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl");
		
		List<TypeA> aList = new ArrayList<TypeA>();
		aList.add(new TypeA(1, "This", "Is wiget A 1"));
		aList.add(new TypeA(2, "This", "Is wiget A 2"));
		aList.add(new TypeA(3, "This", "Is wiget A 3"));
		
		TestWigetAssembly<TestWigetA, TypeA> wa = factory.getAssembly(TestWigetA.class);
		wa.root()
				.set(TestWigetA.model.number, 1)
				.set(TestWigetA.model.alias, "SetAlias");
		
		StringWriter sw = new StringWriter();
		
		wa.output(aList, new PrintWriter(sw)).flush();
		
		String expected = 
				"Test wiget A\n" + 
				"Number: 1\n" + 
				"Alias: SetAlias\n" + 
				"Title: Is wiget A 1\n" +
				"Test wiget A\n" + 
				"Number: 1\n" + 
				"Alias: SetAlias\n" + 
				"Title: Is wiget A 2\n" +
				"Test wiget A\n" + 
				"Number: 1\n" + 
				"Alias: SetAlias\n" + 
				"Title: Is wiget A 3\n";
		System.out.println(sw.toString());
		assertEquals(expected, sw.toString());
		
	}

	@Test
	public void addContainedWigetTest() {

		TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl");
		
		TypeC c = new TypeC("WigetC", "This C", "Is wiget C");
		c.setA(new TypeA(123, "ThisA", "Is embedded in wiget C"));
		c.addB(new TypeB(999L, "ThisB", "Is a member of C"));
		c.addB(new TypeB(998L, "ThisB", "Is also a member of C"));
		c.addB(new TypeB(997L, "ThisB", "Is another member of C"));
		
		TestWigetAssembly<TestWigetC, TypeC> wa = factory.getAssembly(TestWigetC.class);
		wa.root()
			.add(TestWigetC.model.cont1, TestWigetA.class, TestWigetC.model.a)
				.set(TestWigetA.model.alias, "SetAlias")
				.up()
			.add(TestWigetC.model.cont1, TestWigetA.class, TestWigetC.model.a)
				.set(TestWigetA.model.title, "Set Title")
				.root()
			.add(TestWigetC.model.cont2, TestWigetB.class, TestWigetC.model.bs);
		
		StringWriter sw = new StringWriter();
		
		wa.output(c, new PrintWriter(sw)).flush();
		
		String expected = 
				"Test wiget C\n" + 
				"Alias: WigetC\n" + 
				"Name: This C\n" + 
				"Description: Is wiget C\n" + 
				"\n" + 
				"Test wiget A\n" + 
				"Number: 123\n" + 
				"Alias: ThisA\n" + 
				"Title: Is embedded in wiget C\n" + 
				"\n" + 
				"Container C 1 {\n" + 
				"Test wiget A\n" + 
				"Number: 123\n" + 
				"Alias: SetAlias\n" + 
				"Title: Is embedded in wiget C\n" + 
				"Test wiget A\n" + 
				"Number: 123\n" + 
				"Alias: ThisA\n" + 
				"Title: Set Title\n" + 
				"}\n" + 
				"Container C 2 {\n" + 
				"Test wiget B\n" + 
				"Long Number: 999\n" + 
				"Name: ThisB\n" + 
				"Title: Is a member of C\n" + 
				"\n" + 
				"Container B 1 {\n" + 
				"}\n" + 
				"Container B 2 {\n" + 
				"}\n" + 
				"Test wiget B\n" + 
				"Long Number: 998\n" + 
				"Name: ThisB\n" + 
				"Title: Is also a member of C\n" + 
				"\n" + 
				"Container B 1 {\n" + 
				"}\n" + 
				"Container B 2 {\n" + 
				"}\n" + 
				"Test wiget B\n" + 
				"Long Number: 997\n" + 
				"Name: ThisB\n" + 
				"Title: Is another member of C\n" + 
				"\n" + 
				"Container B 1 {\n" + 
				"}\n" + 
				"Container B 2 {\n" + 
				"}\n" + 
				"}\n";
		System.out.println(sw.toString());
		assertEquals(expected, sw.toString());
		
	}

	@Test
	public void addConditionalWigetTest() {

		TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl");
		
		PredicateBuilder pb = new PredicateBuilder();
		
		TypeC c = new TypeC("WigetC", "This C", "Is wiget C");
		c.setA(new TypeA(123, "ThisA", "Is embedded in wiget C"));
		c.addB(new TypeB(999L, "ThisB", "Is a member of C"));
		c.addB(new TypeB(998L, "ThisB", "Is also a member of C"));
		c.addB(new TypeB(997L, "ThisB", "Is another member of C"));
		
		TestWigetAssembly<TestWigetC, TypeC> wa = factory.getAssembly(TestWigetC.class);
		wa.root()
			.addIf(pb.equals(TestWigetC.model.alias, "WigetC"), TestWigetC.model.cont1, TestWigetA.class, TestWigetC.model.a)
				.set(TestWigetA.model.alias, "Included")
				.up()
			.addIf(pb.equals(TestWigetC.model.alias, "XXXX"), TestWigetC.model.cont1, TestWigetA.class, TestWigetC.model.a)
				.set(TestWigetA.model.alias, "NotIncluded")
				.root();
		
		StringWriter sw = new StringWriter();
		
		wa.output(c, new PrintWriter(sw)).flush();
		
		String expected = 
				"Test wiget C\n" + 
				"Alias: WigetC\n" + 
				"Name: This C\n" + 
				"Description: Is wiget C\n" + 
				"\n" + 
				"Test wiget A\n" + 
				"Number: 123\n" + 
				"Alias: ThisA\n" + 
				"Title: Is embedded in wiget C\n" + 
				"\n" + 
				"Container C 1 {\n" + 
				"Test wiget A\n" + 
				"Number: 123\n" + 
				"Alias: Included\n" + 
				"Title: Is embedded in wiget C\n" + 
				"}\n" + 
				"Container C 2 {\n" + 
				"}\n";
		System.out.println(sw.toString());
		assertEquals(expected, sw.toString());
		
	}


	
	
	
	
	
	
	
	
	
	
	
	
}
