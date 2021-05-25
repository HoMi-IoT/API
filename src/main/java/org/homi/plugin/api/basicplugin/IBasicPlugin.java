package org.homi.plugin.api.basicplugin;

import java.util.List;

import org.homi.plugin.api.Commander;
import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.specification.ISpecification;

public interface IBasicPlugin extends IPlugin{
	
	abstract public <T extends Enum<T> & ISpecification> Commander<T> getCommander(Class<? extends ISpecification> spec) throws PluginException;
	abstract public List<Runnable> getWorkers() throws PluginException;
	abstract public List<Class<? extends ISpecification>> getSpecifications() throws PluginException;
	
}
