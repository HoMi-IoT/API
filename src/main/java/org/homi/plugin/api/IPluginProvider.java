package org.homi.plugin.api;

import java.util.List;

/**
 * 
 * @author Nicolas Hanout
 *
 * @since 0.0.1
 */
public interface IPluginProvider {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	IPlugin getPluginByID(String id);
	
	/**
	 * 
	 * @param spec
	 * @return
	 */
	List<IPlugin> getPluginsBySpecID(String spec);
	

	/**
	 * 
	 * @param spec
	 */
	void addPluginRegistryListener(IPluginRegistryListener spec);
	
	/**
	 * 
	 * @param spec
	 */
	void removePluginRegistryListener(IPluginRegistryListener spec);
}
