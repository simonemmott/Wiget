package com.k2.Wiget;

/**
 * Container bindings allow the contents of a template assembly to be bound to a container of one or more wigets implemented by the template assembly
 * 
 * @author simon
 *
 * @param <O> The output type of the wigets 
 */
public class ContainerBinding<O> implements ContainedWiget<O> {

	@SuppressWarnings("rawtypes")
	private AssembledWiget sourceAssembly;
	@SuppressWarnings("rawtypes")
	private WigetContainer fromContainer;
	
	/**
	 * Create a container binding that will supply the contents of the given container from the given assembled wiget
	 * @param sourceAssembly		The assemnbled wiget whose containers contents will be injected into the container containing this container binding
	 * @param fromContainer		The container of the assembled wiget that supplies the contents to be output in the container containing this container binding
	 */
	@SuppressWarnings("rawtypes")
	public ContainerBinding(AssembledWiget sourceAssembly, WigetContainer fromContainer) {
		this.sourceAssembly = sourceAssembly;
		this.fromContainer = fromContainer;
	}

	/**
	 * Output the contents of the bound from container of the bound source assembly into the given instance of the output type and return it
	 * @param out	The instance of the wiget output type into which the assembled wigets are being output
	 * @return	The instance of the wiget output type into which the assembled wigets are being output after outputting the contents of the bound wiget container
	 */
	@SuppressWarnings("unchecked")
	public O output(O out) {
		return (O) sourceAssembly.outputContents(fromContainer, out);
	}

}
