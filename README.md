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

Javadoc documentation of this project can be found [here](https://simonemmott.github.io/Wiget)

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
public class TestWigetFactory extends WigetFactory<TestWigetFamily, PrintWriter> { // <--(1)

	public TestWigetFactory(String ... packageNames) {	// <-- (2)
		super(new TestWigetFamily(), PrintWriter.class, packageNames); // <-- (3)
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <W extends Wiget,T> TestWigetAssembly<W,T> getAssembly(Class<W> wigetType) { // <-- (4)
		return (TestWigetAssembly<W,T>) super.getAssembly(TestWigetAssembly.class, wigetType);
	}

}
```

1.	The specific extension of the abstract WigetFactory defines the specific wiget family and output type in the generic type arguments of the WigetFactory
1.	The constructor of the extended wiget factory receives the packages to scan for wiget implementations
1.	The constructor of the extended wiget factory passes an instance of the extended wiget family and the ouput type to the abstract wiget factory with the packages to scan
1.	The extended wiget factory overrides the `getAssembly(...)` method of the abstract wiget factory to ensure that the method returns instances of the specific extension of the abstract wiget assembly

And TestWigetAssembly is:

```java
public class TestWigetAssembly<W extends Wiget,T> extends WigetAssembly<TestWigetFamily,PrintWriter, W,T> { // <-- (1)

	public TestWigetAssembly(									// <-- (2)
			WigetFactory<TestWigetFamily, PrintWriter> factory, 
			Class<W> wigetType) {
		super(factory, wigetType);
	}
	
	// <-- (3)
}
```

1.	The specific extension of the abstract wiget assembly specifies the wiget family and the output type
1.	The constructor of the specific extension of the abstract wiget assembly receives a factory with the appropriate type arguments.  
1.	This example is trivial however since each assembled wiget is linked to the assembly that it is in and an when outputting a wiget the assembled wiget that is driving the wigets values is available, the wiget assembly that is outputting the wiget is also available. Consequently the by endowing the wiget assembly type with the ability to, for example, manage an indentation level or index wigets in an assembly, allows wigets to be aware of each other or how deeply they are embedded or indeed anything else during assembly output


And TestWigetFamily is:

```java
public class TestWigetFamily extends WigetFamily<PrintWriter> {	// <-- (1)

	public TestWigetFamily() {
		super("TestWigetFamily", PrintWriter.class, TestWigetA.class, TestWigetB.class, TestWigetC.class); // <-- (2)
	}
}
```

1.	The specific extension of the abstract wiget family defines the output type of the wiget family
1.	The constructor of the specific extension of the abstract wiget family defines the array of wigets that must be implmented in order for the family to be considered complete and therefore used to instantiate a wiget factory

And TestWigetA is:

```java
@WigetSpecification	// <-- (1)
public interface TestWigetA extends TestWiget<TypeA> { // <-- (2)
	
	public static class Model{	// <-- (3)
		public WigetParameter<TestWigetA, Integer> number;
		public WigetParameter<TestWigetA, String> alias;
		public WigetParameter<TestWigetA, String> title;
		public WigetContainer<TestWigetA> cont1;
		public WigetContainer<TestWigetA> cont2;
	}
	public static final Model model = new Model(); // <-- (4)
	@Override public default Object staticModel() { return model; } // <-- (5)
	@Override public default Class<?> modelType() { return Model.class; } // <-- (6)
}
```

1.	Wiget specifications are annotated with the `@WigetSpecification` annotation
1.	A wiget specification is an interface that extends the wiget families base interface and defines the wigets requires data type
1.	A wiget specification defines a static model type with WigetParameter fields for each of the getter style methods of the wigets requires data type. These are the wigets parameters. The static model also contains WigetContainer fields for each of the containers of the wiget.
1.	A wiget specification includes a static instance of the static model type. The field values of this static instance are populates when the implementation of the wiget is registered by the wiget factory.
1.	A wiget specification includes a method to retrieve the static instance of the static model so that it can be populated during registration of the wiget imlementation by the wiget factory
1.	A wiget specification defines a method to return the class of the static model. This is used to create an instance of the static model each time a wiget assembled. This instance of the static model is used to control the value source for the parameters for that specific instance of wiget in the larger assembly. This makes the use of wiget and wiget assemblies thread safe.

The implementation of TestWigetA in the package `com.k2.Wiget.testWigets.impl` is :

```java
@WigetImplementation	// <-- (1)
public class TestWigetAImpl extends ATestWiget<TypeA> implements TestWigetA{	// <-- (2)
	
	@SuppressWarnings("rawtypes")
	@Override
	public PrintWriter output(	// <-- (3)
			AssembledWiget<TestWigetFamily, PrintWriter, ? extends Wiget, TypeA> a, // <--(4)
			PrintWriter out) {
		
		out.println("Test wiget A");
		out.println("Number: "+a.get(TestWigetA.model.number));	// <--(5)
		out.println("Alias: "+a.get(TestWigetA.model.alias));
		out.println("Title: "+a.get(TestWigetA.model.title));
		
		return out;	// <-- (6)
	}
}
```

1.	Wiget implementations are annotated with the `@WigetImplementation` annotation
1.	A wiget implementation extends the families abstract base wiget, identifes the requires data type of the wiget and implements the interface of the wiget for which it is an implementation
1.	A wiget implementation implements the `output(...)` method of the wiget interface.  The output method of the wiget is used to generate the output of the wiget with a specific set of input values.
1.	The wigets parameter values and contained wigets are supplied through the assembled wiget passed into the wigets `output(...)` method
1.	The value of a particualr parameter is extracted from the assembled wiget by passing the static wiget parameter field of the static model to the `get(...)` method of the wiget assembly
1.	The instance of the output type is returned after it has been updated with the values from the wiget assembly

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

### Wiget Source Data Type

Wigets expect values from a defined data type. However it is not necessary to supply a wiget with an instance of the required data type.

If the wiget factory has an adapter factory set then if the source data received by a wiget is not of the required type then the adapater factory is automatically inspected to adapt the recieved data type to the required data type

If the received source data cannot be adapted to the required data type then a null value is returned for any parameter that has not been explicitly set.

The source below shows creating a wiget factory with an associated adapter factory.

```java
TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl"); // <-- (1)
factory.setAdapterFactory(AdapterFactory.register("com.k2.Wiget.testAdapters")) // <-- (2)
```

1.	Create a wiget factory scanning the given packages for wiget implementations
1.	Assign an adpater factory to the created wiget factory with adapters found in the given packages

###  Using Collections As The Wiget Data Source

If a collation is used to supply source data to a wiget then the collection is iterated over and a the wiget is output for each item in the collection.

Each item in the collection is assessed for compatibility with the wigets required type and adapted if possible/necessary

### Assembling Wigets

An assembly of wigets always starts with a root wiget and is always returned by the `getAssembly(...)` method of the wiget factory.

#### Setting Wiget Parameters

An assembled wiget can set literal values for its parameters.  If the value of a specific parameter has been set on the assemnbled wiget then that value is used rather than any value supplied or not by the data source supplied to the wiget.

The example below shows setting the parameter values on an assembled wiget.

```java
TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl");

TestWigetAssembly<TestWigetA, TypeA> wa = factory.getAssembly(TestWigetA.class);
wa.root()	// <-- (1)
	.set(TestWigetA.model.alias, "SetAlias")	// <-- (2)
	.set(TestWigetA.model.alias, "SetAliasAgain");	// <-- (3)
```

1.	The `root()` method of a wiget assembly return the root assembled wiget.
1.	Wiget parameters are set by calling the `set(...)` method of the assembled wiget and passing in the static wiget parameter from the wigets static model.
1.	Repeated attempts to set the wiget parameter will result in the last value set being used in the output

#### Containing Wigets In Assembled Wigets

An assembled wiget can contain other assembled wigets in its wiget containers.

The example below shows assembling wigets in a wiget assembly

```java
TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl");

TestWigetAssembly<TestWigetC, TypeC> wa = factory.getAssembly(TestWigetC.class);
wa.root()
	.add(TestWigetC.model.cont1, TestWigetA.class, TestWigetC.model.a)	// <-- (1)
		.set(TestWigetA.model.alias, "SetAlias")	// <-- (2)
		.up()	// <-- (3)
	.add(TestWigetC.model.cont1, TestWigetA.class, TestWigetC.model.a)
		.set(TestWigetA.model.title, "Set Title")
		.add(TestWigetC.model.cont2, TestWigetB.class, TestWigetC.model.bs)
			.root();		// <-- (4)
```

1.	The `add(...)` method of a wiget assembly adds a new assembled wiget to the container identified by the container field of the containing wigets static model for the given wiget to be output using the value supplied by the parameter identified by the parameter field of the containing wigets static model.  
1.	The `add(...)` method returns the assembled wiget added and so parameters on the added assembled wiget can be set.
1.	The `up()` method of an assembled wiget return the parent assembled wiget from the wiget assembly.
1.	The `root()` method of an assembled wiget is a short hand method for returning to the root assembled wiget of the wiget assembly. It has the same effect as repeated calls to the `up()` method to return to the root assembled wiget.

Contained wigets can be optionally included in the generated output.

The example below shows adding a conditional wiget assembly 

```java
TestWigetFactory factory = new TestWigetFactory("com.k2.Wiget.testWigets.impl");

PredicateBuilder pb = new PredicateBuilder();	// <-- (1)

TestWigetAssembly<TestWigetC, TypeC> wa = factory.getAssembly(TestWigetC.class);
wa.root()
	.addIf(pb.equals(TestWigetC.model.alias, "WigetC"),	// <-- (2)
			TestWigetC.model.cont1, TestWigetA.class, TestWigetC.model.a)  
		.up()
```

1.	Conditional assembled wigets are conditional on a `Predicate` so we need a predicate builder to supply a predicate
1.	The `addIf(...)` method of an assembled wiget takes the same parameters as the `add(...)` method plus an additional predicate parameter. The predicates are evaluated using the parameter values from the assembled wiget that **CONTAINS** the conditional wiget.

### Developing Wigets

The defintion of a wiget exists in two parts. The wiget specification and the wiget implementation.

The wiget specification is an interface that defines how to interact with the wiget and the wiget implementation is an implementation of that interface.

#### Wiget Specifications

The code below shows an example of a wiget specification.

```java
@WigetSpecification	// <-- (1)
public interface TestWigetA extends TestWiget<TypeA> { // <-- (2)
	
	public static class Model{	// <-- (3)
		public WigetParameter<TestWigetA, Integer> number;
		public WigetParameter<TestWigetA, String> alias;
		public WigetParameter<TestWigetA, String> title;
		public WigetContainer<TestWigetA> cont1;
		public WigetContainer<TestWigetA> cont2;
	}
	public static final Model model = new Model(); // <-- (4)
	@Override public default Object staticModel() { return model; } // <-- (5)
	@Override public default Class<?> modelType() { return Model.class; } // <-- (6)
}
```

1.	Wiget specifications are annotated with the `@WigetSpecification` annotation
1.	A wiget specification is an interface that extends the wiget families base interface and defines the wigets requires data type
1.	A wiget specification defines a static model type with WigetParameter fields for each of the getter style methods of the wigets requires data type. These are the wigets parameters. The static model also contains WigetContainer fields for each of the containers of the wiget.
1.	A wiget specification includes a static instance of the static model type. The field values of this static instance are populates when the implementation of the wiget is registered by the wiget factory.
1.	A wiget specification includes a method to retrieve the static instance of the static model so that it can be populated during registration of the wiget imlementation by the wiget factory
1.	A wiget specification defines a method to return the class of the static model. This is used to create an instance of the static model each time a wiget assembled. This instance of the static model is used to control the value source for the parameters for that specific instance of wiget in the larger assembly. This makes the use of wiget and wiget assemblies thread safe.

#### Wiget Implementations

The code below shows an example of a wiget implementation

```java
@WigetImplementation	// <-- (1)
public class TestWigetAImpl extends ATestWiget<TypeA> implements TestWigetA{	// <-- (2)
	
	@SuppressWarnings("rawtypes")
	@Override
	public PrintWriter output(	// <-- (3)
			AssembledWiget<TestWigetFamily, PrintWriter, ? extends Wiget, TypeA> a, // <--(4)
			PrintWriter out) {
		
		out.println("Test wiget A");
		out.println("Number: "+a.get(TestWigetA.model.number));	// <--(5)
		out.println("Alias: "+a.get(TestWigetA.model.alias));
		out.println("Title: "+a.get(TestWigetA.model.title));
		
		return out;	// <-- (6)
	}
}
```

1.	Wiget implementations are annotated with the `@WigetImplementation` annotation
1.	A wiget implementation extends the families abstract base wiget, identifes the requires data type of the wiget and implements the interface of the wiget for which it is an implementation
1.	A wiget implementation implements the `output(...)` method of the wiget interface.  The output method of the wiget is used to generate the output of the wiget with a specific set of input values.
1.	The wigets parameter values and contained wigets are supplied through the assembled wiget passed into the wigets `output(...)` method
1.	The value of a particualr parameter is extracted from the assembled wiget by passing the static wiget parameter field of the static model to the `get(...)` method of the wiget assembly
1.	The instance of the output type is returned after it has been updated with the values from the wiget assembly

In addition to the `get(...)` method of an assembled wiget to get a parameter value this is also an `outputContents(...)` method.

The example below show an output method of a  wiget implementation that uses the `outputContents(...)` method to include the contents of containers in the wigets output.

```java
public PrintWriter output(
		AssembledWiget<TestWigetFamily, PrintWriter, ? extends Wiget, TypeC> a,
		PrintWriter out) {
	
	out.println("Test wiget C");
	out.println("Container C 1 {");
	out = a.outputContents(TestWigetC.model.cont1, out); // <-- (1) 
	out.println("}");

	out.println("Container C 2 {");
	out = a.outputContents(TestWigetC.model.cont2, out); // <-- (2)
	out.println("}");
	
	return out;
}
```

1.	This call to `outputContents(...)` causes the contents of container `cont1` of this assembled wiget to be included in the output at this point in the order in which they were added to the container.
1.	The call to `outputContents(...)` outputs the contents of container `cont2`











