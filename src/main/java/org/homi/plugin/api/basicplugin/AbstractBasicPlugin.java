package org.homi.plugin.api.basicplugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homi.plugin.api.commander.Commander;
import org.homi.plugin.api.IPlugin;
import org.homi.plugin.api.IPluginProvider;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;

public abstract class AbstractBasicPlugin implements IBasicPlugin{
	private List<Class<? extends ISpecification>>  specifications = new ArrayList<>();
	private Map<String, Commander<? extends ISpecification>> commanders = new HashMap<>();
	private List<Runnable> workers = new ArrayList<>();
	private IPluginProvider pluginProvider;
	
	final public <T extends Enum<T> & ISpecification> Commander<T> getCommander(Class<? extends ISpecification> spec){
		return (Commander<T>) commanders.get(spec.getAnnotation(SpecificationID.class).id());
	};
	
	final public List<Runnable> getWorkers(){		
		return Collections.unmodifiableList(workers);
	};
	
	final public List<Class<? extends ISpecification>> getSpecifications(){
		return Collections.unmodifiableList(this.specifications);
	};
	
	final public void setPluginProvider(IPluginProvider pluginProvider) {
		this.pluginProvider = pluginProvider;
	}
	
	final protected IPluginProvider getPluginProvider() {
		return this.pluginProvider;
	}
	
	final protected <T extends Enum<T> & ISpecification> void addCommander(Class<T> spec,Commander<T> commander) {
		specifications.add(spec);
		commanders.put(spec.getAnnotation(SpecificationID.class).id(), commander);
	}
	
	final protected void addWorker(Runnable worker) {
		this.workers.add(worker);
	}

	public abstract void setup();
	
	public abstract void teardown();
	
}