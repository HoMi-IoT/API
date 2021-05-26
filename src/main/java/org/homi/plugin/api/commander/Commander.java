package org.homi.plugin.api.commander;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.homi.plugin.api.exceptions.InternalPluginException;
import org.homi.plugin.api.internal.ExecutorServiceManager;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.TypeDef;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;

public class Commander<T extends Enum<T> & ISpecification> {
	private Map<?, IReceiver<?>> mappings;
	private Class<T> spec;

	protected Commander(Class<T> spec, Map<String, IReceiver<?>> mappings) {
		this.mappings = mappings;
		this.spec = spec;
	}

	public <C extends Enum<?> & ISpecification, R> R execute(C command, Object... args)
			throws InvalidArgumentException, ArgumentLengthException, InternalPluginException {
		T c = Enum.valueOf(spec, command.name());

		args = Commander.validateParameterTypes(c.getParameterTypes(), args);

		return (R) c.getReturnType().process(this.mappings.get(c.name()).doAction(args));
	}

	public <C extends Enum<?> & ISpecification, R> Future<R> executeAsync(C command, Object... args) {
		return (Future<R>) ExecutorServiceManager.getExecutorService().submit(() -> {
			try {
				return execute(command, args);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	private static Object[] validateParameterTypes(List<TypeDef<?>> parameterTypes, Object[] args)
			throws ArgumentLengthException, InvalidArgumentException {
		if (parameterTypes.size() != args.length)
			throw new ArgumentLengthException(
					"Expected " + parameterTypes.size() + " arguments, but received " + args.length);
		Object[] processedArgs = new Object[args.length];
		for (int i = 0; i < args.length; i++)
			processedArgs[i] = parameterTypes.get(i).process(args[i]);

		return processedArgs;
	}

}
