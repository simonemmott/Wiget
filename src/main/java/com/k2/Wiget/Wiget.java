package com.k2.Wiget;

/**
 * The wiget interface defines the methods common to all wigets
 * 
 * @author simon
 *
 * @param <F>	The wiget family
 * @param <O>	The output type of the wiget
 * @param <T>	The requires data type of the wiget
 */
public interface Wiget<F extends WigetFamily<O>,O,T> {

	/**
	 * @return The string alias that uniquely identifies this wiget
	 */
	public String alias();
	/**
	 * 
	 * @return 	The data type ordinarity required by this wiget.  If the type of the datasource supplied to the wiget is not of this
	 * data type then the adapter factory asscociated with the wiget factory that supplies the wiget is inspected to attempt to adapt the supplied
	 * data type into the required data type. If no such adapter exists then the wigets parameter will all return null unless they have been set to
	 * a literal value or bound to another paramter.
	 */
	public Class<T> requiresType();
	/**
	 * @return The class of the static Model for this wiget.
	 * This is used to create an instance of the static model on each wiget assembly that assembled this wiget to hold the parameter values and bindings etc
	 * set on the assembled wiget
	 */
	public abstract Class<?> modelType();
	/**
	 * @return An instance of the static model populated with mapped wiget parameters and wiget containers
	 */
	public abstract Object generateModel();
	/**
	 * @return	The static instance of the model for this wiget
	 */
	public abstract Object staticModel();
	
	/**
	 * Output this wiget onto the given output instance using the parameter values and contained wigets supplied by the given assembled wiget.
	 * @param assembly	The assembled wiget that supplied values for the parameter for this wiget and contents for the containers of this wiget
	 * @param out		The instance of the output type into which this wiget should be output
	 * @return			The instance of the output type after this wiget has been output
	 */
	@SuppressWarnings("rawtypes")
	public O output(AssembledWiget<F,O,? extends Wiget, T> assembly, O out);

}
