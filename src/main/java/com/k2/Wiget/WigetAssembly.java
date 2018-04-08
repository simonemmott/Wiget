package com.k2.Wiget;

/**
 * A wiget assmbly is an hierarchy of assembled wigets.
 * Each assembled wiget in the hierarchy is linked directly to the wiget assembly and as such the wiget assemble can be used to
 * provide support services such a indexing and indentation to wigets in the assembly.
 * @author simon
 *
 * @param <F>	The wiget family
 * @param <O>	The output type
 * @param <W>	The type of the assemblies root wiget
 * @param <T>	The requires type of this assemblies root wiget.
 */
@SuppressWarnings("rawtypes")
public class WigetAssembly<F extends WigetFamily<O>, O, W extends Wiget,T> {

	private final WigetFactory<F,O> factory;
	/**
	 * @return	The wiget factory used to supply wigets implementations in this family
	 */
	public WigetFactory<F,O> factory() { return factory; }
	
	private final AssembledWiget<F,O,W,T> root;
	/**
	 * @return The root assembled wiget for this wiget assembly
	 */
	public AssembledWiget<F,O,W,T> root() { return root; }
	
	/**
	 * Create a wiget assembly for the given wiget factory and root wiget type.
	 * @param factory	The wiget factory that supplies wiget implementations for the required wiget family
	 * @param wigetType	The type of the root wiget for the wiget assembly
	 */
	@SuppressWarnings("unchecked")
	protected WigetAssembly(WigetFactory<F,O> factory, Class<W> wigetType) {
		this.factory = factory;
		this.root = new AssembledWiget(this, wigetType);
	}
	
	/**
	 * Output the root assembled wiget and all of its contained wigets into the given instance of the output type using the given data source object
	 * @param dataSource		The object to use as the datasource for the wiget assembly
	 * @param out			The instance of the ouptut type in which to output the wiget assembly
	 * @return				The instance of the output type after outputting the assembly
	 */
	public O output(Object dataSource, O out) {
		return root.output(dataSource, out);
	}
	
	@SuppressWarnings("unchecked")
	public <Q extends Wiget> AssembledWiget<F,O,Q,?> assemble(Class<Q> wigetType) {
		return new AssembledWiget(this, wigetType);
	}
}
