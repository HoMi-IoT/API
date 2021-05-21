package org.homi.plugin.api;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;

import org.homi.plugin.api.exceptions.InternalPluginException;
import org.homi.plugin.api.internal.ExecutorServiceManager;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.TypeDef;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;

public class Commander<T extends Enum<T> & ISpecification> {
	private Map<?, IReceiver> mappings;
	private Class<T> spec;

	protected Commander(Class<T> spec,Map<?, IReceiver> mappings){
		this.mappings = mappings;
		this.spec = spec;
	}

	public <C extends Enum<?> & ISpecification, R> R execute(C command, Object ...args) throws InvalidArgumentException, ArgumentLengthException, InternalPluginException {
		T c = Enum.valueOf(spec, command.name());
		
		args = Commander.validateParameterTypes(c.getParameterTypes(), args);
		
		return (R) c.getReturnType().process(this.mappings.get(c.name()).doAction(args));
	}

	public <C extends Enum<?> & ISpecification,R> Future<R> executeAsync(C command, Object ...args) {
		return (Future<R>) ExecutorServiceManager.getExecutorService()
				.submit(() ->{
					return execute(command, args);
					});
	}

	private static Object[] validateParameterTypes(TypeDef<?>[] parameterTypes, Object[] args) throws ArgumentLengthException, InvalidArgumentException {
		if(parameterTypes.length != args.length)
			throw new ArgumentLengthException("Expected "+parameterTypes.length+" arguments, but received "+args.length);
		Object[] processedArgs = new Object[args.length];
		for(int i=0; i<args.length; i++)
			processedArgs[i] = parameterTypes[i].process(args[i]);
		
		return processedArgs;
	}

}
