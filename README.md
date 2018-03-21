# Wiget - v0.1.0
The Wiget project provides a powerful abstraction that allows wigets (simple classes providing a single `output(...)` method) to be assembled into complex assembles of wigets that together
provide a much more complex output.

The Wiget project defines the minimum requirement for a wiget to allow wigets to be assembled in to into a hierarchy of wigets, a wiget assembly. 
Wigets receive data in the form of parameters and may have 1 or more containers that can contain other wigets. In such a way wigets can be assembled into a hierarchy with each node on the hierarchy being a wiget and and the child nodes (wigets) grouped into one or more distinct containers.

The value of a wigets parameters is extracgted from a data source object or can be set to a literal value when the wiget in assembled into a container of another wiget.

When a wiget is assembled into the container of another wiget the value of one of the containing wigets parameters is identified as providing the data source value for the contained wiget.

The single `output(...)` method of a wiget receives an instance of the output data type and adjusts that instance in accordance with the logic defined within the output method using the parameter values provided to it before returning the output of the output method. 
If a wiget includes containers then the output of the wigets contained in the wigets containers are also added to output returned by the call to the `output(...)` method in the order in which they were added to the wigets containers. The location of the contents of the containers within the resultant output is entirely the dependent on the logic of the of the wiget however all contained wigets will be output together and in order.

The data type required by and returned by the `output(...)` method of a wiget is not prescribed by the wiget project but is prescribed by a given wiget. A wiget can only contain other wigets that output the same type as the containing wiget.

Wigets are designed to collaborate with other wigets to generate more complex output where the more complex output can be broken down into multiple repeatable items. This collaborative nature and the requirement for wigets to share the same output type in order to be assembled together is encapsulated in the WigetFamily.  A wiget family defines the output type of all the wigets in the family and wigets exist in only one family.  A family of wigets also defines the minimum set of wigets that must be implemented in order for the family to be considered complete.

It should be noted that a particular wiget does not define exactly how a wiget should generate its output or indeed the precise format of that output, rather is represents an agreement to generate part of a more complex output that satisfies a particular requirement. A wiget specification is therefore an interface in java and a particular implementation of that interface is an implementation of the wiget. The wiget specification defines the data type required by the wiget which in turn identifies all the parameters that can be set on an assembly of that wiget. It also defines the containers in the wiget. The wiget implementation defines how the parameter values of an assembled wigets are combined to produce an output and where/when in that output the output of the contained wigets will be included.

The identification of the appropriate implementation of a wiget to include in an assembly of wigets is controlled by the WigetFactory.  The wiget factory scans a set of packages searching for wiget implementations of a particular family and supplies an instance of the implementation of a given wiget specification when requested. Consequently the assembly of wigets is independent of the absolute implementation of the wiget specification.

The requires type of a wiget can also be an interface and as such the specification of a wiget can be completely decoupled from the usage of that wiget and the absolute source of data for the wiget as will as from the specific implementation of the wiget.

Wigets therefore define a completely abstracted mechanism to assemble multiple distinct and dirrering outputs from a set of common parts.

An assembly of wigets can itself be implemented as a wiget in the same family generating the same output type a the wigets that make up the wiget assembly. Implementing an assembly of wiget in this way effectively predefines an assembly of wigets that can themselves be used as a wiget. I.e. included in other wigets etc. This approach allows wigets to be pre-assembled into templates.

The wiget project includes an extension of the WigetFamily, WigetFactory and WigetAssembly specifically for generating wiget templates as a pre-assembly of known wigets. These implementations of the WigetFamily, WigetFactory and WigetAssembly together form the TemplateWriter that generates the java source code for wiget templates (wiget specifications and wiget implementations the implement an pre-defined assembly of wigets independently of the implementation of the assembled wigets). Due to the abstraction offered by the Wiget prject the absolute datasource of the TemplateWriter is not constrained only that the object supplied to the template writer must implement the `TemplateDef` interface.

Javadoc documentation of this project can be found [here](https://simonemmott.github.io/Wiget/index.html)

### License

[GNU GENERAL PUBLIC LICENSE v3](http://fsf.org/)

## Basic Example

The WigetFactory provides the an assembly of wigets starting with the given root wiget type and allows other wigets to be added to the assembly programatically.

In order to use the wiget factory a family of wigets must be defined and a specific extension of the wiget factory must be created to interact with the abstract wiget factory for wigets in the wiget family.

This code:
```java
TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl"); // <-- (1)

TypeA a = new TypeA(10, "This", "Is wiget A"); // <-- (2)

TestWigetAssembly<TestWigetA, TypeA> wa = factory.getAssembly(TestWigetA.class); // <-- (3)

wa.output(a, new PrintWriter(System.out)).flush(); // <--(4)
```

Produces the following output

```java
Test wiget A
Number: 10 
Alias: This 
Title: Is wiget A
```

1.	Create an implementation of the TestWigetFactory scanning the package `com.k2.Wiget.testWigets.impl` and all its sub packages for wiget implementations in the `TestWigetFamily`
1.	Create an instance of the TypeA object settings some values
1.	Use the test wiget factory created in (1) to generate a wiget assembly with TestWigetAImpl found in the package `com.k2.Wiget.testWigets.impl` as its root wiget
1.	Output the data created in (2) using the implementation of `TestWigetA` on the console.

Where TestWigetFactory is:

```java
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
```

And TestWigetAssembly is:

```java
public class TestWigetAssembly<W extends Wiget,T> extends WigetAssembly<TestWigetFamily,PrintWriter, W,T> {

	public TestWigetAssembly(
			WigetFactory<TestWigetFamily, PrintWriter> factory, 
			Class<W> wigetType) {
		super(factory, wigetType);
	}
}
```

And TestWigetFamily is:

```java
public class TestWigetFamily extends WigetFamily<PrintWriter> {

	public TestWigetFamily() {
		super("TestWigetFamily", PrintWriter.class, TestWigetA.class, TestWigetB.class, TestWigetC.class);
	}
}
```

And TestWigetA is:

```java
@WigetSpecification
public interface TestWigetA extends TestWiget<TypeA> {
	
	public static class Model{
		public WigetParameter<TestWigetA, Integer> number;
		public WigetParameter<TestWigetA, String> alias;
		public WigetParameter<TestWigetA, String> title;
		public WigetContainer<TestWigetA> cont1;
		public WigetContainer<TestWigetA> cont2;
	}
	public static final Model model = new Model();
	@Override public default Object staticModel() { return model; }
	@Override public default Class<?> modelType() { return Model.class; }
}
```

The implementation of TestWigetA in the package `com.k2.Wiget.testWigets.impl` is :

```java
@WigetImplementation
public class TestWigetAImpl extends ATestWiget<TypeA> implements TestWigetA{
	
	@SuppressWarnings("rawtypes")
	@Override
	public PrintWriter output(
			AssembledWiget<TestWigetFamily, PrintWriter, ? extends Wiget, TypeA> a,
			PrintWriter out) {
		
		out.println("Test wiget A");
		out.println("Number: "+a.get(TestWigetA.model.number));
		out.println("Alias: "+a.get(TestWigetA.model.alias));
		out.println("Title: "+a.get(TestWigetA.model.title));
		
		return out;
	}
}
```

And TypeA is:

```java
public class TypeA {

	private int number;
	public int getNumber() { return number; }
	private String alias;
	public String getAlias() { return alias; }
	private String title;
	public String getTitle() { return title; }
	
	public TypeA(int number, String alias, String title) {
		this.number = number;
		this.alias = alias;
		this.title = title;
	}	
}
```


## Getting Started

Download a jar file containing the latest version or fork this project and install in your IDE

Maven users can add this project using the following additions to the pom.xml file.
```maven
<dependencies>
    ...
    <dependency>
        <groupId>com.k2</groupId>
        <artifactId>Wiget</artifactId>
        <version>0.1.0</version>
    </dependency>
    ...
</dependencies>
```

## Working With Wigets



