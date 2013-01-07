package de.crowdcode.movmvn.core;

/**
 * Interface for plugin.
 * 
 * @author lofi
 */
public interface Plugin {

	/**
	 * Execute the plugin.
	 * 
	 * @param context
	 *            context information for the plugin
	 */
	public void execute(Context context);

	/**
	 * Get the name of the plugin.
	 * 
	 * @return the name of the plugin
	 */
	public String getName();

	/**
	 * Get an integer number for ordering the execution of the plugin.
	 * 
	 * @return an integer as an ordering number
	 */
	public int getExecutionOrderedNumber();
}
