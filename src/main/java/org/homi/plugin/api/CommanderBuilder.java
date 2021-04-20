package org.homi.plugin.api;

import java.util.HashMap;
import java.util.Map;

import org.homi.plugin.specification.ISpecification;

/**
 * 
 * @author Nicolas Hanout
 *
 * @param <T>
 * @since 0.0.1
 */
public class CommanderBuilder<T extends Enum<T> & ISpecification> {
	
	private Class<T> spec;
	private Map<String, IReceiver> mappings;

	/**
	 * 
	 * @param spec
	 */
	public CommanderBuilder(Class<T> spec) {
		this.spec = spec;
		this.mappings = new HashMap<>();
	}
	
	/**
	 * 
	 * @param command
	 * @param action
	 * @return
	 */
	public CommanderBuilder<T> onCommandEquals(T command, IReceiver action) {
		mappings.put(command.name(), action);
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public Commander<T> build() {
		return new Commander<T>(spec, mappings);
	}

}
