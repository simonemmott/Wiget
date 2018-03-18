package com.k2.Wiget;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k2.Util.FileUtil;
import com.k2.Wiget.templateFactory.TypeDefImpl;
import com.k2.Wiget.templateFactory.WigetContainerBindingDefImpl;
import com.k2.Wiget.templateFactory.WigetParameterBindingDefImpl;
import com.k2.Wiget.templateFactory.AssembledWigetDefImpl;
import com.k2.Wiget.templateFactory.ContainerDefImpl;
import com.k2.Wiget.templateFactory.FamilyDefImpl;
import com.k2.Wiget.templateFactory.MethodDefImpl;
import com.k2.Wiget.templateFactory.TemplateAssembly;
import com.k2.Wiget.templateFactory.TemplateDefImpl;
import com.k2.Wiget.templateFactory.TemplateFactory;
import com.k2.Wiget.templateFactory.TemplateWriter;
import com.k2.Wiget.templateFactory.TemplateWriterException;
import com.k2.Wiget.templateFactory.spec.TemplateImplementation;
import com.k2.Wiget.templateFactory.spec.TemplateSpecification;
import com.k2.Wiget.templateFactory.types.FamilyDef;
import com.k2.Wiget.templateFactory.types.MethodDef;
import com.k2.Wiget.templateFactory.types.TemplateDef;
import com.k2.Wiget.templateFactory.types.TypeDef;
import com.k2.Wiget.testAssemblies.impl.AssemblyC_1Impl;
import com.k2.Wiget.testAssemblies.spec.AssemblyC_1;
import com.k2.Wiget.testTypes.*;
import com.k2.Wiget.testWigets.TestWigetAssembly;
import com.k2.Wiget.testWigets.TestWigetFactory;
import com.k2.Wiget.testWigets.TestWigetFamily;
import com.k2.Wiget.testWigets.spec.*;

public class TemplateFactoryTest {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	TemplateFactory factory = new TemplateFactory("com.k2.Wiget.templateFactory.impl");
	
	FamilyDef familyDef = new FamilyDefImpl(TestWigetFamily.class)
			.setOutput(PrintWriter.class)
			.setFactory(TestWigetFactory.class)
			.setAssembly(TestWigetAssembly.class)
			.setAbstractWiget(ATestWiget.class);
	
	TypeDef typeA = new TypeDefImpl(TypeA.class);
	TypeDef typeB = new TypeDefImpl(TypeB.class);
	TypeDef listTypeB = new TypeDefImpl(List.class, typeB);
	TypeDef typeC = new TypeDefImpl(TypeC.class)
			.add(new MethodDefImpl("getAlias", TypeDef.StringDef, MethodDef.GETTER_METHOD))
			.add(new MethodDefImpl("getName", TypeDef.StringDef, MethodDef.GETTER_METHOD))
			.add(new MethodDefImpl("getDescription", TypeDef.StringDef, MethodDef.GETTER_METHOD))
			.add(new MethodDefImpl("getA", typeA, MethodDef.GETTER_METHOD))
			.add(new MethodDefImpl("setA", TypeDef.voidDef, MethodDef.SETTER_METHOD))
			.add(new MethodDefImpl("addB", TypeDef.voidDef, MethodDef.CLASS_METHOD))
			.add(new MethodDefImpl("getBs", listTypeB, MethodDef.GETTER_METHOD))
			.add(new MethodDefImpl("setBs", TypeDef.voidDef, MethodDef.SETTER_METHOD));
	
	TypeDef testWigetA = new TypeDefImpl(TestWigetA.class);
	TypeDef testWigetB = new TypeDefImpl(TestWigetB.class);
	TypeDef testWigetC = new TypeDefImpl(TestWigetC.class);

	
	TemplateDef template = new TemplateDefImpl(AssemblyC_1.class, AssemblyC_1Impl.class)
			.setFamilyDef(familyDef)
			.setExtendsWigetInterface(new TypeDefImpl(TestWiget.class))
			.setRequiresType(typeC)
			.add(new ContainerDefImpl("cont1"))
			.setAssemblesWiget(
					new AssembledWigetDefImpl(testWigetC)
					.add(WigetParameterBindingDefImpl.set("alias", "SetAlias"))
					.add(WigetParameterBindingDefImpl.bind("name", "alias"))
					.add(WigetParameterBindingDefImpl.bind("description", "name"))
					.add(WigetContainerBindingDefImpl.containedAssembly("cont1", testWigetA, "a")
						.containedWigetAssembly()
							.add(WigetParameterBindingDefImpl.set("alias", "SetAlias"))
						.up()
					)
					.add(WigetContainerBindingDefImpl.containedAssembly("cont1", testWigetA, "a")
						.containedWigetAssembly()
							.add(WigetParameterBindingDefImpl.set("title", "Set Title"))
						.up()
					)
					.add(WigetContainerBindingDefImpl.containedAssembly("cont2", testWigetB, "bs")
						.containedWigetAssembly()
							.add(WigetParameterBindingDefImpl.bind("description", "description"))
							.add(WigetContainerBindingDefImpl.boundContainer("cont2", "cont1"))
						.up()
					)
					);

	@Test
	public void templateSpecificationSourceTest() {

		
		TemplateAssembly<TemplateSpecification, TemplateDef> wa = factory.getAssembly(TemplateSpecification.class);	
		
		StringWriter sw = new StringWriter();
		
		wa.output(template, new PrintWriter(sw)).flush();
		
		String expected = 
				"package com.k2.Wiget.testAssemblies.spec;\n" + 
				"\n" + 
				"import com.k2.Wiget.WigetContainer;\n" + 
				"import com.k2.Wiget.WigetParameter;\n" + 
				"import com.k2.Wiget.annotation.WigetSpecification;\n" + 
				"import com.k2.Wiget.testTypes.TypeA;\n" + 
				"import com.k2.Wiget.testTypes.TypeB;\n" + 
				"import com.k2.Wiget.testTypes.TypeC;\n" + 
				"import com.k2.Wiget.testWigets.spec.TestWiget;\n" + 
				"import java.lang.String;\n" + 
				"import java.util.List;\n" + 
				"\n" + 
				"@WigetSpecification()\n" + 
				"public interface AssemblyC_1 extends TestWiget<TypeC> {\n" + 
				"\n" + 
				"	public static class Model {\n" + 
				"		public WigetParameter<AssemblyC_1, String> alias;\n" + 
				"		public WigetParameter<AssemblyC_1, String> name;\n" + 
				"		public WigetParameter<AssemblyC_1, String> description;\n" + 
				"		public WigetParameter<AssemblyC_1, TypeA> a;\n" + 
				"		public WigetParameter<AssemblyC_1, List<TypeB>> bs;\n" + 
				"		public WigetContainer<AssemblyC_1> cont1;\n" + 
				"	}\n" + 
				"\n" + 
				"	public static final Model model = new Model();\n" + 
				"	@Override public default Object staticModel() { return model; }\n" + 
				"	@Override public default Class<?> modelType() { return Model.class; }\n" + 
				"\n" + 
				"}\n";
//		System.out.println(sw.toString());
		assertEquals(expected, sw.toString());
		
	}

	@Test
	public void templateImplementationSourceTest() {

		
		TemplateAssembly<TemplateImplementation, TemplateDef> wa = factory.getAssembly(TemplateImplementation.class);	
		
		StringWriter sw = new StringWriter();
		
		wa.output(template, new PrintWriter(sw)).flush();
		
		String expected = 
				"package com.k2.Wiget.testAssemblies.impl;\n" + 
				"\n" + 
				"import com.k2.Wiget.AssembledWiget;\n" + 
				"import com.k2.Wiget.Wiget;\n" + 
				"import com.k2.Wiget.annotation.WigetImplementation;\n" + 
				"import com.k2.Wiget.testAssemblies.spec.AssemblyC_1;\n" + 
				"import com.k2.Wiget.testTypes.TypeC;\n" + 
				"import com.k2.Wiget.testWigets.TestWigetAssembly;\n" + 
				"import com.k2.Wiget.testWigets.TestWigetFactory;\n" + 
				"import com.k2.Wiget.testWigets.TestWigetFamily;\n" + 
				"import com.k2.Wiget.testWigets.spec.ATestWiget;\n" + 
				"import com.k2.Wiget.testWigets.spec.TestWigetA;\n" + 
				"import com.k2.Wiget.testWigets.spec.TestWigetB;\n" + 
				"import com.k2.Wiget.testWigets.spec.TestWigetC;\n" + 
				"import java.io.PrintWriter;\n" + 
				"\n" + 
				"@WigetImplementation\n" + 
				"public class AssemblyC_1Impl extends ATestWiget<TypeC> implements AssemblyC_1{\n" + 
				"	@SuppressWarnings(\"rawtypes\")\n" + 
				"	@Override\n" + 
				"	public PrintWriter output(\n" + 
				"			AssembledWiget<TestWigetFamily, PrintWriter, ? extends Wiget, TypeC> a,\n" + 
				"			PrintWriter out) {\n" + 
				"		\n" + 
				"		TestWigetFactory factory = (TestWigetFactory) a.assembly().factory();\n" + 
				"		\n" + 
				"		TestWigetAssembly<TestWigetC, TypeC> templateAssembly = factory.getAssembly(TestWigetC.class);\n" + 
				"		templateAssembly.root()\n" + 
				"			.set(TestWigetC.model.alias, \"SetAlias\")\n" + 
				"			.bind(TestWigetC.model.name, a, AssemblyC_1.model.alias)\n" + 
				"			.bind(TestWigetC.model.description, a, AssemblyC_1.model.name)\n" + 
				"			.add(TestWigetC.model.cont1, TestWigetA.class, TestWigetC.model.a)\n" + 
				"				.set(TestWigetA.model.alias, \"SetAlias\")\n" + 
				"				.up()\n" + 
				"			.add(TestWigetC.model.cont1, TestWigetA.class, TestWigetC.model.a)\n" + 
				"				.set(TestWigetA.model.title, \"Set Title\")\n" + 
				"				.up()\n" + 
				"			.add(TestWigetC.model.cont2, TestWigetB.class, TestWigetC.model.bs)\n" + 
				"				.bind(TestWigetB.model.description, a, AssemblyC_1.model.description)\n" + 
				"				.bind(TestWigetB.model.cont2, a, AssemblyC_1.model.cont1)\n" + 
				"				.up()\n" + 
				"			.up()\n" + 
				"			;\n" + 
				"		\n" + 
				"		return templateAssembly.output(a.getDataSource(), out);\n" + 
				"	}\n" + 
				"}\n";
//		System.out.println(sw.toString());
		assertEquals(expected, sw.toString());
		
	}

	@Test
	public void templateWriterTest() throws TemplateWriterException {
		File testOutput = new File("testOutput");
		for (File f : testOutput.listFiles()) 
			FileUtil.deleteCascade(f);
		
		TemplateWriter tw = new TemplateWriter(testOutput);

		assertFalse(testOutput.toPath().resolve("com").toFile().exists());		
		
		tw.write(template);
		
		assertTrue(testOutput.toPath().resolve("com").toFile().exists());
		assertTrue(testOutput.toPath().resolve("com").resolve("k2").toFile().exists());
		assertTrue(testOutput.toPath().resolve("com").resolve("k2").resolve("Wiget").toFile().exists());
		assertTrue(testOutput.toPath().resolve("com").resolve("k2").resolve("Wiget").resolve("testAssemblies").toFile().exists());
		assertTrue(testOutput.toPath().resolve("com").resolve("k2").resolve("Wiget").resolve("testAssemblies").resolve("impl").toFile().exists());
		assertTrue(testOutput.toPath().resolve("com").resolve("k2").resolve("Wiget").resolve("testAssemblies").resolve("impl").resolve("AssemblyC_1Impl.java").toFile().exists());
		assertTrue(testOutput.toPath().resolve("com").resolve("k2").resolve("Wiget").resolve("testAssemblies").resolve("spec").toFile().exists());
		assertTrue(testOutput.toPath().resolve("com").resolve("k2").resolve("Wiget").resolve("testAssemblies").resolve("spec").resolve("AssemblyC_1.java").toFile().exists());
	}
	
	
	
}
