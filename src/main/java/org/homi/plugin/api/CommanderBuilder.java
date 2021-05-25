package org.homi.plugin.api;

import java.util.HashMap;
import java.util.Map;

import org.homi.plugin.specification.ISpecification;

public class CommanderBuilder<T extends Enum<T> & ISpecification> {
	
	private Class<T> spec;
	private Map<String, IReceiver<?>> mappings;

	public CommanderBuilder(Class<T> spec) {
		this.spec = spec;
		this.mappings = new HashMap<>();
	}

	public CommanderBuilder<T> onCommandEquals(T command, IReceiver<?> action) {
		mappings.put(command.name(), action);
		return this;
	}

	public Commander<T> build() {
		return new Commander<T>(spec, mappings);
	}

}
