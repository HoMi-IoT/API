package org.homi.plugin.api;

/**
 * 
 * @author Nicolas Hanout
 *
 * @since 0.0.1
 */
public interface IPluginRegistryListener {
	/**
	 * 
	 * @param plugin
	 */
	void addPlugin(IPlugin plugin);
	
	/**
	 * 
	 * @param plugin
	 */
	void removePlugin(IPlugin plugin);
}
