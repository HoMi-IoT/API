package org.homi.plugin.api;

public interface IPlugin {
	public default String id() {
		return this.getClass().getAnnotation(PluginID.class).id();
	}
}
