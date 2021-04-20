package org.homi.plugin.api;

import java.util.Map;
import java.util.concurrent.Future;

import org.homi.plugin.api.internal.ExecutorServiceManager;
import org.homi.plugin.specification.ISpecification;

/**
 * 
 * @author Nicolas Hanout
 * 
 * @param <T>
 * @since 0.0.1
 */
public class Commander<T extends Enum<T> & ISpecification> {
	private Map<?, IReceiver> mappings;
	private Class<T> spec;
	
	/**
	 * 
	 * @param spec
	 * @param mappings
	 */
	Commander(Class<T> spec,Map<?, IReceiver> mappings){
		this.mappings = mappings;
		this.spec = spec;
	}
	
	/**
	 * 
	 * @param <C>
	 * @param <R>
	 * @param command
	 * @param args
	 * @return
	 * @throws TypeMismatchException
	 * @throws ClassCastException
	 * @throws IllegalArgumentException
	 */
	public <C extends Enum<?> & ISpecification, R> R execute(C command, Object ...args) throws TypeMismatchException, ClassCastException, IllegalArgumentException {
		T c = Enum.valueOf(spec, command.name());
		
		if (!Commander.areValidParameterTypes(c.getParameterTypes(), args))
			throw new TypeMismatchException();
		
		return (R) c.getReturnType().cast(this.mappings.get(c.name()).doAction(args));
	}

	public <C extends Enum<?> & ISpecification,R> Future<R> executeAsync(C command, Object ...args) throws TypeMismatchException, ClassCastException, IllegalArgumentException {

		T c = Enum.valueOf(spec, command.name());
		
		if(!Commander.areValidParameterTypes(c.getParameterTypes(), args))
			throw new TypeMismatchException();
		
		return (Future<R>) ExecutorServiceManager.getExecutorService()
				.submit(() ->{ 
					return c.getReturnType().cast(mappings.get(command.name()).doAction(args));
					});
	}

	/**
	 * 
	 * @param parameterTypes
	 * @param args
	 * @return
	 */
	private static boolean areValidParameterTypes(Class<?>[] parameterTypes, Object[] args) {
		if(parameterTypes.length != args.length)
			return false;
		
		for(int i=0; i<args.length; i++) {
			try {
				parameterTypes[i].cast(args[i]);
			} catch(Exception e) {
				throw new TypeMismatchException();
			}
		}
		return true;
		
	}

}
