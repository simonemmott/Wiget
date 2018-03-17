package com.k2.Wiget;

public class ContainerBinding<O> implements ContainedWiget<O> {

	@SuppressWarnings("rawtypes")
	private AssembledWiget sourceAssembly;
	@SuppressWarnings("rawtypes")
	private WigetContainer fromContainer;
	
	@SuppressWarnings("rawtypes")
	public ContainerBinding(AssembledWiget sourceAssembly, WigetContainer fromContainer) {
		this.sourceAssembly = sourceAssembly;
		this.fromContainer = fromContainer;
	}

	@SuppressWarnings("unchecked")
	public O output(O out) {
		return (O) sourceAssembly.outputContents(fromContainer, out);
	}

}
