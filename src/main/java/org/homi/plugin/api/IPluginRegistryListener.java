package org.homi.plugin.api;

public interface IPluginRegistryListener {
	
	void addPlugin(IPlugin plugin);
	
	void removePlugin(IPlugin plugin);
}
