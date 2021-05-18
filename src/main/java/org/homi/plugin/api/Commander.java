package org.homi.plugin.api;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;

import org.homi.plugin.api.internal.ExecutorServiceManager;
import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.ParameterType;

public class Commander<T extends Enum<T> & ISpecification> {
	private Map<?, IReceiver> mappings;
	private Class<T> spec;

	protected Commander(Class<T> spec,Map<?, IReceiver> mappings){
		this.mappings = mappings;
		this.spec = spec;
	}

	public <C extends Enum<?> & ISpecification, R> R execute(C command, Object ...args) throws TypeMismatchException, ClassCastException, IllegalArgumentException, ClassNotFoundException, IOException {
		T c = Enum.valueOf(spec, command.name());
		
		if (!Commander.validateParameterTypes(c.getParameterTypes(), args))
			throw new TypeMismatchException();
		
		return (R) c.getReturnType().cast(this.mappings.get(c.name()).doAction(args));
	}

	public <C extends Enum<?> & ISpecification,R> Future<R> executeAsync(C command, Object ...args) {
		return (Future<R>) ExecutorServiceManager.getExecutorService()
				.submit(() ->{
					return execute(command, args);
					});
	}

	private static boolean validateParameterTypes(ParameterType<?>[] parameterTypes, Object[] args) {
		if(parameterTypes.length != args.length) {
			System.out.println("ARGUMENTS OF DIFFERENT LENGTHS, EXPECTED (" + parameterTypes.length + ") BUT RECIEVED (" + args.length + ")");
			return false;
		}
		
		for(int i=0; i<args.length; i++) {
			System.out.print("casting to " + parameterTypes[i].getTypeClass().getName());
			try {
				args[i] = parameterTypes[i].cast(args[i]);
				System.out.println(" Success");
			} catch(Exception e) {
				System.out.println(" failed");
				throw new TypeMismatchException();
			}
		}
		return true;
	}

}
