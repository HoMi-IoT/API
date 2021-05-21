package org.homi.plugin.api;

import java.util.List;

public interface IPluginProvider {
	
	IPlugin getPluginByID(String id);
	
	List<IPlugin> getPluginsBySpecID(String spec);
	
	void addPluginRegistryListener(IPluginRegistryListener spec);
	
	void removePluginRegistryListener(IPluginRegistryListener spec);
}
