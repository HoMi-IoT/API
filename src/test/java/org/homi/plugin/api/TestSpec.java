package org.homi.plugin.api;

import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.ParameterType;
import org.homi.plugin.specification.SpecificationID;

@SpecificationID(id = "TestSpec")
public enum TestSpec implements ISpecification{

	RETURN_NULL(new ParameterType<>(Void.class)),
	RETURN_STRING(new ParameterType<>(String.class)),
	RETURN_INTEGER(new ParameterType<>(Integer.class)),
	RETURN_FLOAT(new ParameterType<>(Float.class)),
	RETURN_OBJECT(new ParameterType<>(Object.class)),

	RETURN_WRONG_TYPE(new ParameterType<>(Float.class)),
	

	SEND_STRING(new ParameterType<>(String.class), new ParameterType<>(String.class)),
	SEND_INTEGER(new ParameterType<>(Void.class), new ParameterType<>(Integer.class)),
	SEND_CUSTOM(new ParameterType<>(Void.class), new ParameterType<>(Custom.class));
	

	private ParameterType<?>[] parameterTypes;
	private ParameterType<?> returnType;
	TestSpec(ParameterType<?> returnType, ParameterType<?> ...parameterTypes ) {
		this.parameterTypes = parameterTypes;
		this.returnType = returnType;
	}

	@Override
	public ParameterType<?>[] getParameterTypes() {
		return this.parameterTypes;
	}
	
	@Override
	public ParameterType<?> getReturnType() {
		return this.returnType;
	}
	
}