package com.k2.Wiget;


@SuppressWarnings("rawtypes")
public class WigetAssembly<F extends WigetFamily<O>, O, W extends Wiget,T> {

	private final WigetFactory<F,O> factory;
	public WigetFactory<F,O> factory() { return factory; }
	
	private final AssembledWiget<F,O,W,T> root;
	public AssembledWiget<F,O,W,T> root() { return root; }
	
	@SuppressWarnings("unchecked")
	protected WigetAssembly(WigetFactory<F,O> factory, Class<W> wigetType) {
		this.factory = factory;
		this.root = new AssembledWiget(this, wigetType);
	}
	
	public O output(Object dataSource, O out) {
		return root.output(dataSource, out);
	}
	
}
