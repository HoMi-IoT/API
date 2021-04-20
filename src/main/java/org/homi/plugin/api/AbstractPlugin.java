package org.homi.plugin.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;

/**
 * 
 * @author Nicolas Hanout
 * @since 0.0.1
 */
public abstract class AbstractPlugin implements IPlugin{
	private List<Class<? extends ISpecification>>  specifications = new ArrayList<>();
	private Map<String, Commander<? extends ISpecification>> commanders = new HashMap<>();
	private Map<String, List<Runnable>> workers = new HashMap<>();
	private IPluginProvider pluginProvider;
	
	/**
	 * 
	 * @param <T>
	 * @param spec
	 * @return
	 */
	final public <T extends Enum<T> & ISpecification> Commander<T> getCommander(Class<? extends ISpecification> spec){
		return (Commander<T>) commanders.get(spec.getAnnotation(SpecificationID.class).id());
	};
	
	/**
	 * 
	 * @param <T>
	 * @param spec
	 * @return
	 */
	final public <T extends Enum<T> & ISpecification> List<Runnable> getWorkers(Class<? extends ISpecification> spec){
		if(spec ==null)
			return Collections.unmodifiableList(workers.getOrDefault(null, Collections.unmodifiableList(Collections.EMPTY_LIST)));
		
		return Collections.unmodifiableList(workers.getOrDefault(spec.getAnnotation(SpecificationID.class).id(),Collections.unmodifiableList(Collections.EMPTY_LIST)));
	};
	
	/**
	 * 
	 * @return
	 */
	final public List<Class<? extends ISpecification>> getSpecifications(){
		return Collections.unmodifiableList(this.specifications);
	};
	
	/**
	 * @return
	 */
	final public int getAPIVersion() {
		return 0;
	}
	
	/**
	 * @return
	 */
	final public Class<AbstractPlugin> getKind(){
		return AbstractPlugin.class;
	}
	
	/**
	 * 
	 * @param pluginProvider
	 */
	final public void setPluginProvider(IPluginProvider pluginProvider) {
		this.pluginProvider = pluginProvider;
	}
	
	/**
	 * 
	 * @return
	 */
	final protected IPluginProvider getPluginProvider() {
		return this.pluginProvider;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param spec
	 * @param commander
	 */
	final protected <T extends Enum<T> & ISpecification> void addCommander(Class<T> spec,Commander<T> commander) {
		specifications.add(spec);
		commanders.put(spec.getAnnotation(SpecificationID.class).id(), commander);
	}
	
	/**
	 * 
	 * @param <T>
	 * @param spec
	 * @param worker
	 */
	final protected <T extends ISpecification> void addWorker(Class<T> spec, Runnable worker) {
		if (spec == null) {
			this.workers.putIfAbsent(null,  new ArrayList<>());
			this.workers.get(null).add(worker);
		}else {
			this.workers.putIfAbsent(spec.getAnnotation(SpecificationID.class).id(), new ArrayList<>());
			this.workers.get(spec.getAnnotation(SpecificationID.class).id()).add(worker);
		}
	}
	
	public abstract void setup();
	
}